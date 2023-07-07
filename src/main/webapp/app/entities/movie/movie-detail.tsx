import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { DurationFormat } from 'app/shared/DurationFormat';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './movie.reducer';

export const MovieDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const movieEntity = useAppSelector(state => state.movie.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="movieDetailsHeading">
          <Translate contentKey="movieApp.movie.detail.title">Movie</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{movieEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="movieApp.movie.name">Name</Translate>
            </span>
          </dt>
          <dd>{movieEntity.name}</dd>
          <dt>
            <span id="duration">
              <Translate contentKey="movieApp.movie.duration">Duration</Translate>
            </span>
          </dt>
          <dd>
            {movieEntity.duration ? <DurationFormat value={movieEntity.duration} /> : null} ({movieEntity.duration})
          </dd>
          <dt>
            <span id="description">
              <Translate contentKey="movieApp.movie.description">Description</Translate>
            </span>
          </dt>
          <dd>{movieEntity.description}</dd>
          <dt>
            <span id="language">
              <Translate contentKey="movieApp.movie.language">Language</Translate>
            </span>
          </dt>
          <dd>{movieEntity.language}</dd>
          <dt>
            <span id="imageUrl">
              <Translate contentKey="movieApp.movie.imageUrl">Image Url</Translate>
            </span>
          </dt>
          <dd>{movieEntity.imageUrl}</dd>
          <dt>
            <span id="publishDate">
              <Translate contentKey="movieApp.movie.publishDate">Publish Date</Translate>
            </span>
          </dt>
          <dd>{movieEntity.publishDate ? <TextFormat value={movieEntity.publishDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="movieApp.movie.membreStaff">Membre Staff</Translate>
          </dt>
          <dd>
            {movieEntity.membreStaffs
              ? movieEntity.membreStaffs.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {movieEntity.membreStaffs && i === movieEntity.membreStaffs.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="movieApp.movie.category">Category</Translate>
          </dt>
          <dd>
            {movieEntity.categories
              ? movieEntity.categories.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {movieEntity.categories && i === movieEntity.categories.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/movie" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/movie/${movieEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default MovieDetail;
