package br.com.jca.services;

import br.com.jca.response.UuidResponse;
import com.github.f4b6a3.uuid.UuidCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.*;
import java.util.UUID;


@Slf4j
@Service
public class UuidService {


    public UuidResponse uuidUtc() {
        UuidResponse response = new UuidResponse();

        // Generate a time-ordered UUIDv7 based on the current UTC epoch timestamp
        UUID uuidV7 = UuidCreator.getTimeOrderedEpoch();
        String uuidAsString = uuidV7.toString();
        response.setUuid(uuidAsString);
        response.setTimestampUtc(new Timestamp(extractTimestampFromUUIDv7(uuidV7)));
        response.setDataTimeNow(response.getTimestampUtc().toLocalDateTime());
        ZonedDateTime zonedDateTime = response.getDataTimeNow().atZone(ZoneId.of("America/Sao_Paulo"));
        Instant instant = zonedDateTime.toInstant();
        LocalDateTime dateTime = instant.atZone(ZoneId.of("America/Sao_Paulo")).toLocalDateTime();
        response.setTimestamp(zonedDateTime);
        response.setDateTimeUtc(LocalDateTime.ofInstant(
                Instant.ofEpochMilli(response.getTimestampUtc().getTime()),
                ZoneOffset.UTC));


        return response;
    }

    public static long extractTimestampFromUUIDv7(UUID uuid) {
        // Get the most significant bits (MSB) of the UUID
        long msb = uuid.getMostSignificantBits();

        // The first 48 bits of the MSB contain the Unix epoch timestamp in milliseconds.
        // We need to mask out the version bits (0111 at bits 60-63) and any other data.
        // The timestamp occupies bits 0-47 of the MSB.
        // The version bits are at positions 60-63 (0111 for v7).
        // To get the raw timestamp, we can shift the MSB right by 16 bits
        // to effectively remove the version and variant information and
        // align the 48-bit timestamp to the lower end of a 64-bit long.
        // However, the RFC 9562 specifies the timestamp as the first 48 bits,
        // so we need to ensure we're isolating those.
        // A simpler way is to mask the MSB to get only the relevant 48 bits.
        // The mask for the 48-bit timestamp is 0xFFFFFFFFFFFF0000L (bits 0-47).
        // However, the version bits are within the MSB.
        // The timestamp is encoded in the most significant 48 bits of the UUID.
        // The version bits are in the 13th to 16th bits of the MSB (from the right).
        // The variant bits follow the version.

        // A more direct approach given the RFC 9562 structure:
        // The timestamp is the first 48 bits.
        // We need to mask out the version and variant bits that are embedded later.
        // The version bits are at positions 60-63 (0111).
        // The variant bits are at positions 64-65 (10 for RFC 4122).

        // To get the 48-bit timestamp:
        // 1. Shift the most significant bits to the right by 16 to remove the version and variant.
        //    This effectively aligns the 48-bit timestamp to the lower 48 bits of the long.
        long timestampMillis = msb >>> 16;

        return timestampMillis;
    }


}
