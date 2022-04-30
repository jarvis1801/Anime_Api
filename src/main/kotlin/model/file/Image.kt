package com.jarvis.acg.api.model.file

import io.ktor.http.content.*
import java.io.ByteArrayInputStream
import javax.imageio.ImageIO

class Image(
    var imageWidth: Int? = null,
    var imageHeight: Int? = null
) : BaseFile() {

    fun initMetaInfo() {
        partData?.let {
            fileBytes = it.streamProvider().readBytes()
            val input = ImageIO.createImageInputStream(ByteArrayInputStream(fileBytes))
            val readers = ImageIO.getImageReaders(input)
            while (readers.hasNext()) {
                val reader = readers.next()
                reader.input = input
                imageWidth = reader.getWidth(0)
                imageHeight = reader.getHeight(0)
            }
        }

        file?.let {
            fileBytes = it.readBytes()
            val input = ImageIO.createImageInputStream(ByteArrayInputStream(fileBytes))
            val readers = ImageIO.getImageReaders(input)
            while (readers.hasNext()) {
                val reader = readers.next()
                reader.input = input
                imageWidth = reader.getWidth(0)
                imageHeight = reader.getHeight(0)
            }
        }
    }
}