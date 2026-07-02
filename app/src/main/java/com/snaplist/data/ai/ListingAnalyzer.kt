package com.snaplist.data.ai

import android.util.Log
import com.anthropic.client.okhttp.AnthropicOkHttpClient
import com.anthropic.core.JsonValue
import com.anthropic.errors.AnthropicIoException
import com.anthropic.errors.AnthropicServiceException
import com.anthropic.errors.RateLimitException
import com.anthropic.errors.UnauthorizedException
import com.anthropic.models.messages.Base64ImageSource
import com.anthropic.models.messages.ContentBlockParam
import com.anthropic.models.messages.ImageBlockParam
import com.anthropic.models.messages.JsonOutputFormat
import com.anthropic.models.messages.MessageCreateParams
import com.anthropic.models.messages.OutputConfig
import com.anthropic.models.messages.TextBlockParam
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

/** User-presentable analyzer failures. */
class AnalyzerException(message: String, cause: Throwable? = null) : Exception(message, cause)

private const val TAG = "SnapListAnalyzer"

class ListingAnalyzer {

    /**
     * One Claude API call: all photos as base64 image blocks + instruction text,
     * response constrained to the listing JSON schema via output_config.
     */
    suspend fun analyze(
        apiKey: String,
        model: String,
        photos: List<File>,
        country: String,
        currency: String,
    ): ListingAnalysis = withContext(Dispatchers.IO) {
        require(photos.isNotEmpty()) { "No photos to analyze" }
        if (apiKey.isBlank()) {
            throw AnalyzerException("No API key set. Add your Anthropic API key in Settings.")
        }

        val client = AnthropicOkHttpClient.builder().apiKey(apiKey).build()
        try {
            val blocks = buildList {
                photos.forEach { file ->
                    add(
                        ContentBlockParam.ofImage(
                            ImageBlockParam.builder()
                                .source(
                                    Base64ImageSource.builder()
                                        .mediaType(Base64ImageSource.MediaType.IMAGE_JPEG)
                                        .data(ImagePrep.toBase64Jpeg(file))
                                        .build()
                                )
                                .build()
                        )
                    )
                }
                add(
                    ContentBlockParam.ofText(
                        TextBlockParam.builder().text(ListingPrompt.USER_INSTRUCTION).build()
                    )
                )
            }

            val params = MessageCreateParams.builder()
                .model(model)
                .maxTokens(4000L)
                .system(ListingPrompt.systemPrompt(country, currency))
                .outputConfig(
                    OutputConfig.builder()
                        .format(
                            JsonOutputFormat.builder()
                                .schema(schemaFromMap(ListingPrompt.schema))
                                .build()
                        )
                        .build()
                )
                .addUserMessageOfBlockParams(blocks)
                .build()

            val response = client.messages().create(params)
            val text = response.content()
                .mapNotNull { block -> block.text().map { it.text() }.orElse(null) }
                .joinToString("")
            if (text.isBlank()) {
                throw AnalyzerException("The model returned no listing. Try again.")
            }
            try {
                ListingAnalysis.parse(text)
            } catch (e: Exception) {
                throw AnalyzerException("Could not read the model's answer. Try again.", e)
            }
        } catch (e: UnauthorizedException) {
            Log.e(TAG, "analysis failed: unauthorized", e)
            throw AnalyzerException("Invalid API key. Check it in Settings.", e)
        } catch (e: RateLimitException) {
            Log.e(TAG, "analysis failed: rate limited", e)
            throw AnalyzerException("Rate limited by the API. Wait a minute and retry.", e)
        } catch (e: AnthropicIoException) {
            Log.e(TAG, "analysis failed: network", e)
            throw AnalyzerException("Network problem. Check your connection and retry.", e)
        } catch (e: AnthropicServiceException) {
            Log.e(TAG, "analysis failed: API ${e.statusCode()}", e)
            val detail = e.message?.take(300).orEmpty()
            throw AnalyzerException("API error (${e.statusCode()}): $detail", e)
        } finally {
            client.close()
        }
    }

    private fun schemaFromMap(map: Map<String, Any?>): JsonOutputFormat.Schema {
        val builder = JsonOutputFormat.Schema.builder()
        map.forEach { (key, value) -> builder.putAdditionalProperty(key, JsonValue.from(value)) }
        return builder.build()
    }
}
