package com.snaplist.data.photos

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import androidx.core.content.FileProvider
import java.io.File

/**
 * Photos live in app-private storage under filesDir/photos/<draftId>/ and are
 * exposed to other apps (Vinted) through the FileProvider declared in the manifest.
 */
class PhotoStore(private val context: Context) {

    fun dirFor(draftId: Long): File =
        File(File(context.filesDir, "photos"), draftId.toString()).apply { mkdirs() }

    fun newPhotoFile(draftId: Long): File =
        File(dirFor(draftId), "photo_${System.currentTimeMillis()}.jpg")

    /** Copy a content:// URI (gallery pick) into the draft's photo dir. */
    fun importUri(draftId: Long, uri: Uri): File {
        val dest = newPhotoFile(draftId)
        context.contentResolver.openInputStream(uri).use { input ->
            requireNotNull(input) { "Cannot open $uri" }
            dest.outputStream().use { output -> input.copyTo(output) }
        }
        return dest
    }

    fun shareUri(file: File): Uri =
        FileProvider.getUriForFile(context, "com.snaplist.fileprovider", file)

    fun deletePhotos(draftId: Long) {
        dirFor(draftId).deleteRecursively()
    }

    /**
     * Copy the draft's photos into the shared gallery (Pictures/SnapList) so
     * Vinted's own photo picker can see them. Fallback path for when the direct
     * share into Vinted isn't used. Returns the number of photos exported.
     */
    fun exportToGallery(paths: List<String>): Int {
        val resolver = context.contentResolver
        var exported = 0
        paths.map(::File).filter(File::exists).forEach { file ->
            val values = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, file.name)
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/SnapList")
            }
            val uri = resolver.insert(
                MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY),
                values,
            ) ?: return@forEach
            resolver.openOutputStream(uri)?.use { output ->
                file.inputStream().use { it.copyTo(output) }
                exported++
            }
        }
        return exported
    }
}
