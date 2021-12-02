package com.olx.util;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

import com.olx.dto.Advertises;
import com.olx.entity.PostEntity;

public class AdvertiseConverterUtil {
	
	
	public static  Advertises convertEntityToDto( ModelMapper modelMapper,PostEntity advertiseEntity) {
		modelMapper.getConfiguration().setAmbiguityIgnored(true);
		TypeMap<PostEntity, Advertises> typeMap = modelMapper.typeMap(PostEntity.class, Advertises.class);
		typeMap.addMapping(PostEntity::getCategory, Advertises::setCategoryId);
		typeMap.addMapping(PostEntity::getStatus, Advertises::setStatusId);
		return modelMapper.map(advertiseEntity, Advertises.class);
		}



		public static PostEntity convertDtoToEntity(ModelMapper modelMapper, Advertises advertise) {
		modelMapper.getConfiguration().setAmbiguityIgnored(true);
		TypeMap<Advertises, PostEntity> typeMap = modelMapper.typeMap(Advertises.class, PostEntity.class);
		typeMap.addMapping(Advertises::getCategoryId, PostEntity::setCategory);
		typeMap.addMapping(Advertises::getStatusId, PostEntity::setStatus);
		return modelMapper.map(advertise, PostEntity.class);
		}



		public static List<Advertises> convertEntityToDto(ModelMapper modelMapper, List<PostEntity> advertiseEntityList) {
		return advertiseEntityList
		.stream()
		.map(advertiseEntity -> modelMapper.map(advertiseEntity, Advertises.class))
		.collect(Collectors.toList());
		}
}
