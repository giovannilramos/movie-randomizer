package br.com.giovanniramos.movie_randomizer.repositories.impl;

import br.com.giovanniramos.movie_randomizer.entities.MovieEntity;
import br.com.giovanniramos.movie_randomizer.models.MovieModel;
import br.com.giovanniramos.movie_randomizer.repositories.MovieCriteriaQueriesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class MovieCriteriaQueriesRepositoryImpl implements MovieCriteriaQueriesRepository {
    private final MongoTemplate mongoTemplate;

    @Override
    public Page<MovieEntity> findAllByFilters(final MovieModel movieModel, final Pageable pageable) {
        final var query = createDynamicFilters(movieModel);
        query.with(pageable);
        final var movieEntityList = mongoTemplate.find(query, MovieEntity.class);
        final var total = mongoTemplate.count(Query.of(query).limit(-1).skip(-1), MovieEntity.class);
        return new PageImpl<>(movieEntityList, pageable, total);
    }

    private Query createDynamicFilters(final MovieModel movieModel) {
        final var query = new Query();

        if (nonNull(movieModel.getName()) && !movieModel.getName().isBlank()) {
            query.addCriteria(Criteria.where("name").regex(".*" + movieModel.getName() + ".*", "i"));
        }

        if (nonNull(movieModel.getMovieType())) {
            query.addCriteria(Criteria.where("movieType").is(movieModel.getMovieType()));
        }

        if (nonNull(movieModel.getGenres()) && !movieModel.getGenres().isEmpty()) {
            query.addCriteria(Criteria.where("genres").in(movieModel.getGenres()));
        }

        if (nonNull(movieModel.getNote())) {
            query.addCriteria(Criteria.where("note").is(movieModel.getNote()));
        }

        if (nonNull(movieModel.getAddedBy()) && !movieModel.getAddedBy().isBlank()) {
            query.addCriteria(Criteria.where("addedBy").regex(".*" + movieModel.getAddedBy() + ".*", "i"));
        }

        if (nonNull(movieModel.getIsFirstTimeWatching())) {
            query.addCriteria(Criteria.where("isFirstTimeWatching").is(movieModel.getIsFirstTimeWatching()));
        }

        if (nonNull(movieModel.getDuration())) {
            query.addCriteria(Criteria.where("duration").lte(movieModel.getDuration()));
        }

        return query;
    }
}
