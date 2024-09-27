package org.grating.recolldroid.data

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.net.URL

/**
 * Encapsulate all fields returned by recoll server for a single search result.
 */
@Serializable
data class RecollResult(
    @Serializable(with = UrlSerializer::class)
    val url: URL,
    val sig: Long,
    @SerialName(value = "rclmbreaks")
    @Serializable(with = RecollMultiBreaks::class)
    val recollMultiBreaks: List<Pair<Long,Long>> = emptyList(),
    @SerialName(value = "ipath")
    val iPath: String,
    @SerialName(value = "rcludi")
    val recollUdi: String,
    val title: String,
    @SerialName(value = "fbytes")
    val fBytes: Long,
    val abstract: String,
    val author: String = UNKNOWN,
    val caption: String = UNKNOWN,
    @SerialName(value = "dbytes")
    val dBytes: Long,
    val filename: String,
    @SerialName(value = "relevancyrating")
    @Serializable(with = RelevancyRatingSerializer::class)
    val relevancyRatingPercent: Double,
    @SerialName(value = "fmtime")
    val fmTime: Long,
    @SerialName(value = "mtype")
    @Serializable(with = DocTypeSerializer::class)
    val mType: DocType,
    @SerialName(value = "origcharset")
    val origCharset: String,
    @SerialName(value = "mtime")
    val mTime: Long,
    @SerialName(value = "pcbytes")
    val pcBytes: Long
) {
    companion object {
        const val UNKNOWN: String = "UNKNOWN"
    }
}

object UrlSerializer : KSerializer<URL> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("URL", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): URL {
        return URL(decoder.decodeString())
    }

    override fun serialize(encoder: Encoder, value: URL) {
        encoder.encodeString(value.toString())
    }
}

object DocTypeSerializer : KSerializer<DocType> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("DocType", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): DocType {
        return DocType.BY_TEXT[decoder.decodeString()] ?: DocType.UNKNOWN
    }

    override fun serialize(encoder: Encoder, value: DocType) {
        encoder.encodeString(value.text)
    }
}

object RelevancyRatingSerializer : KSerializer<Double> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("RelevancyRating", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): Double {
        return decoder.decodeString().trim().dropLast(1).toDouble()
    }

    override fun serialize(encoder: Encoder, value: Double) {
        return encoder.encodeString("${value}%")
    }
}

object RecollMultiBreaks : KSerializer<List<Pair<Double,Double>>> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("RecollMultiBreaks", PrimitiveKind.STRING)

    // Oh man look how easy this is!?
    override fun deserialize(decoder: Decoder): List<Pair<Double, Double>> {
        return decoder.decodeString().split(',').chunked(2) {
            (first, second) -> Pair(first.toDouble(), second.toDouble())
        }
    }

    override fun serialize(encoder: Encoder, value: List<Pair<Double, Double>>) {
        return encoder.encodeString(value.joinToString {
            "${it.first},${it.second}"
        })
    }

}