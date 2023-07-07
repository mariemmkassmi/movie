import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IStaff } from 'app/shared/model/staff.model';
import { getEntities as getStaff } from 'app/entities/staff/staff.reducer';
import { ICategory } from 'app/shared/model/category.model';
import { getEntities as getCategories } from 'app/entities/category/category.reducer';
import { IMovie } from 'app/shared/model/movie.model';
import { Langue } from 'app/shared/model/enumerations/langue.model';
import { getEntity, updateEntity, createEntity, reset } from './movie.reducer';

export const MovieUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const staff = useAppSelector(state => state.staff.entities);
  const categories = useAppSelector(state => state.category.entities);
  const movieEntity = useAppSelector(state => state.movie.entity);
  const loading = useAppSelector(state => state.movie.loading);
  const updating = useAppSelector(state => state.movie.updating);
  const updateSuccess = useAppSelector(state => state.movie.updateSuccess);
  const langueValues = Object.keys(Langue);

  const handleClose = () => {
    navigate('/movie');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getStaff({}));
    dispatch(getCategories({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.publishDate = convertDateTimeToServer(values.publishDate);

    const entity = {
      ...movieEntity,
      ...values,
      membreStaffs: mapIdList(values.membreStaffs),
      categories: mapIdList(values.categories),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          publishDate: displayDefaultDateTime(),
        }
      : {
          language: 'Francais',
          ...movieEntity,
          publishDate: convertDateTimeFromServer(movieEntity.publishDate),
          membreStaffs: movieEntity?.membreStaffs?.map(e => e.id.toString()),
          categories: movieEntity?.categories?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="movieApp.movie.home.createOrEditLabel" data-cy="MovieCreateUpdateHeading">
            <Translate contentKey="movieApp.movie.home.createOrEditLabel">Create or edit a Movie</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="movie-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label="Name" id="movie-name" name="name" data-cy="name" type="text" />
              <ValidatedField label="Duration" id="movie-duration" name="duration" data-cy="duration" type="text" />
              <ValidatedField label="Description" id="movie-description" name="description" data-cy="description" type="textarea" />
              <ValidatedField label="Language" id="movie-language" name="language" data-cy="language" type="select">
                {langueValues.map(langue => (
                  <option value={langue} key={langue}>
                    {langue}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField label="Image Url" id="movie-imageUrl" name="imageUrl" data-cy="imageUrl" type="text" />
              <ValidatedField
                label="Publish Date"
                id="movie-publishDate"
                name="publishDate"
                data-cy="publishDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField label="Membre Staff" id="movie-membreStaff" data-cy="membreStaff" type="select" multiple name="membreStaffs">
                <option value="" key="0" />
                {staff
                  ? staff.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField label="Category" id="movie-category" data-cy="category" type="select" multiple name="categories">
                <option value="" key="0" />
                {categories
                  ? categories.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/movie" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default MovieUpdate;
