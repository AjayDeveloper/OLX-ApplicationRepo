package com.olx.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.olx.dto.Advertises;
import com.olx.entity.PostEntity;
import com.olx.exception.InVaildAuthTokenException;
import com.olx.exception.InvalidPostIdException;
import com.olx.repository.PostRepository;
import com.olx.repository.SearchCriteriaSpecification;
import com.olx.util.AdvertiseConverterUtil;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	PostRepository postRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	LoginDelegate loginDelegate;
	
	
	@Autowired
	MasterDataDelegate masterDataDelegate;

	@Override
	public Advertises createNewPost(String authToken, Advertises advertises) {

		if (!loginDelegate.isValidateToken(authToken)) {
			throw new InVaildAuthTokenException(authToken);
		}

		System.out.println("AUTH-TOKEN - " + authToken);
		PostEntity postEntity = this.modelMapper.map(advertises, PostEntity.class);
		System.out.println("PostEntity" + postEntity.toString());
		postEntity = this.postRepository.save(postEntity);
		System.out.println("PostEntity after save" + postEntity.toString());
		Advertises advertisDto = this.modelMapper.map(postEntity, Advertises.class);
		System.out.println("createNewPost" + advertisDto.toString());
		return advertisDto;
	}

	private List<Advertises> getAdvertisesDtoList(List<PostEntity> postEntityList) {
		List<Advertises> postDtoList = new ArrayList<Advertises>();
		for (PostEntity postEntity : postEntityList) {
			Advertises advertisesDto = this.modelMapper.map(postEntity, Advertises.class);
			postDtoList.add(advertisesDto);
		}
		return postDtoList;
	}

	@Override
	public Advertises updatePost(String authToken, int id, Advertises advertises) {
		if (!loginDelegate.isValidateToken(authToken)) {
			throw new InVaildAuthTokenException(authToken);
		}

		System.out.println("AUTH-TOKEN - " + authToken);
		Optional<PostEntity> opPostEntity = this.postRepository.findById(id);
		if (opPostEntity.isPresent()) {
			PostEntity postEntity = opPostEntity.get();
			advertises.setId(id);
			postEntity = this.modelMapper.map(advertises, PostEntity.class);
			postEntity = this.postRepository.save(postEntity);
			Advertises advertisesDto = this.modelMapper.map(postEntity, Advertises.class);
			return advertisesDto;

		}
		throw new InvalidPostIdException("" + id);
	}

	@Override
	public List<Advertises> getAllAdvertisementsPostByUser(String authToken) {
		if (!loginDelegate.isValidateToken(authToken)) {
			throw new InVaildAuthTokenException(authToken);
		}
		List<PostEntity> postEntitiesList = postRepository.findAll();
		List<Advertises> advertisesList = new ArrayList<Advertises>();
		for (PostEntity postEntity : postEntitiesList) {
			Advertises advertisesDto = this.modelMapper.map(postEntity, Advertises.class);
			advertisesList.add(advertisesDto);
		}
		return advertisesList;
	}

	@Override
	public Advertises getAllAdvertisementsPostByUserId(String authToken, int id) {
		if (!loginDelegate.isValidateToken(authToken)) {
			throw new InVaildAuthTokenException(authToken);
		}
		Optional<PostEntity> opPostEntity = postRepository.findById(id);
		if (opPostEntity.isPresent()) { // Correct PostId
			PostEntity postEntity = opPostEntity.get();
			Advertises advertisesDto = this.modelMapper.map(postEntity, Advertises.class);
			return advertisesDto;
		}
		throw new InvalidPostIdException("" + id);
	}

	@Override
	public boolean deleteAdvertisementByPostId(String authToken, int id) {
		if (!loginDelegate.isValidateToken(authToken)) {
			throw new InVaildAuthTokenException(authToken);
		}
		Optional<PostEntity> opPostEntity = postRepository.findById(id);
		if (opPostEntity.isPresent()) {
			postRepository.deleteById(id);
			return true;
		} else {
			throw new InvalidPostIdException("" + id);
		}

	}

	@Override
	public List<Advertises> findByTitle(String title) {
		List<PostEntity> postEntities = postRepository.findByTitle(title);
		return getAdvertisesDtoList(postEntities);
	}

	@Override
	public List<Advertises> searchAdvertisementBySearchCriteria(String searchText, int categoryId, String postedBy, String dateCondition, LocalDate onDate, LocalDate fromDate, LocalDate toDate, String sortBy, String sortOn, int startIndex, int records, int statusId) {
		Sort sort;
		if (sortBy.equalsIgnoreCase("asc")) {
		sort = Sort.by(Sort.Direction.ASC, sortOn);
		} else {
		sort = Sort.by(Sort.Direction.DESC, sortOn);
		}

		Pageable pageWithFewRecords = PageRequest.of(startIndex, records, sort);

		SearchCriteriaSpecification spec1 = new SearchCriteriaSpecification(
		searchText,
		categoryId,
		postedBy,
		dateCondition,
		onDate,
		fromDate,
		toDate,
		statusId
		);
		Page<PostEntity> advertiseEntities = postRepository.findAll(spec1, pageWithFewRecords);

		return AdvertiseConverterUtil.convertEntityToDto(modelMapper, advertiseEntities.getContent());
	}

}
