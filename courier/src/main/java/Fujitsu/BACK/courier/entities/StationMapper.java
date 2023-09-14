package Fujitsu.BACK.courier.entities;

import Fujitsu.BACK.courier.dto.StationDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface StationMapper {
    Station toEntity(StationDto stationDto);

    StationDto toDto(Station station);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Station partialUpdate(StationDto stationDto, @MappingTarget Station station);
}