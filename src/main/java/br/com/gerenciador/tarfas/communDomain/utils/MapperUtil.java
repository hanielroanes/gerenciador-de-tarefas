package br.com.gerenciador.tarfas.communDomain.utils;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MapperUtil {
    /**
     * Model mapper.
     */
    protected ModelMapper modelMapper;

    /**
     * Default Constructor.
     */
    public MapperUtil() {
        this.modelMapper = new ModelMapper();
    }

    /**
     * Maps of form strict the source to destination class.
     *
     * @param source    Source.
     * @param destClass Destination class.
     * @return Instance of destination class.
     */
    public  <S, D> D strictMapTo(S source, Class<D> destClass){
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return this.modelMapper.map(source, destClass);
    }

    /**
     * Maps the source to destination class.
     *
     * @param source    Source.
     * @param destClass Destination class.
     * @return Instance of destination class.
     */
    public <S, D> D mapTo(S source, Class<D> destClass) {
        return this.modelMapper.map(source, destClass);
    }

    /**
     * Maps the source to destination type class.
     *
     * @param source    Source.
     * @param destClass Destination class.
     * @return Instance of destination class.
     */
    public <S, D> D mapTo(S source, Type destClass) {
        return this.modelMapper.map(source, destClass);
    }

    /**
     * Converts a source to a type destination.
     *
     * @param source                The source object
     * @param destination            The destination object
     * @return                        The object created
     */
    public <T, E> E updateTo(T source, E destination) {
        this.modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        this.modelMapper.map(source, destination);
        return destination;
    }

    /**
     * Converts the source list to a destination list mapping the source items to
     * destination type items.
     *
     * @param source    Source items.
     * @param destClass Destination class.
     * @return List of instances of destination type.
     */
    public <S, T> List<T> toList(List<S> source, Class<T> destClass) {
        return source.stream().map(s -> this.modelMapper.map(s, destClass)).collect(Collectors.toList());
    }

    /**
     * Converts the source list of form strict to a destination list mapping the source items to
     * destination type items.
     *
     * @param source    Source items.
     * @param destClass Destination class.
     * @return List of instances of destination type.
     */
    public <S, T> List<T> strictToList(List<S> source, Class<T> destClass) {
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return source.stream().map(s -> this.modelMapper.map(s, destClass)).collect(Collectors.toList());
    }

    /**
     * Maps the Page {@code entities} of <code>T</code> type which have to be mapped as input to {@code destClass} Page
     * of mapped object with <code>D</code> type.
     *
     * @param <D> - type of objects in result page
     * @param <T> - type of entity in <code>entityPage</code>
     * @param entities - page of entities that needs to be mapped
     * @param destClass - class of result page element
     * @return page - mapped page with objects of type <code>D</code>.
     */
    public <D, T> Page<D> toPage(Page<T> entities, Class<D> destClass) {
        return entities.map(objectEntity -> modelMapper.map(objectEntity, destClass));
    }

    /**
     * Maps the list for a Paginated List
     *
     * @param list - list of entities that will be paging
     * @param pageRequest - information to page the list
     * @param <S> - list entity type
     * @return list - paged list
     */
    public <S> List<S> paginateList(List<S> list, Pageable pageRequest){

        var paged = new PagedListHolder<>(list);
        paged.setPage(pageRequest.getPageNumber());
        paged.setPageSize((pageRequest.getPageSize()));

        return pageRequest.getPageNumber() < paged.getPageCount() ? paged.getPageList() : new ArrayList<>();
    }
}
