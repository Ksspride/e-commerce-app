package com.kss.customer.utils;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.Collection;
import java.util.List;

public class ObjectMapperUtils {
    private static ModelMapper modelMapper = new ModelMapper();

    private ObjectMapperUtils() {
    }

    public static <D, T> D map(final T entity, Class<D> outClass) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(entity, outClass);
    }

    public static <D, T> List<D> mapAll(final Collection<T> entityList, Class<D> outCLass) {
        return entityList.stream().map(entity -> map(entity, outCLass)).toList();
    }

    public static <S, D> D map(final S source, D destination) {
        modelMapper.map(source, destination);
        return destination;
    }
}
