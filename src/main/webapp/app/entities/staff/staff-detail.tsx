import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './staff.reducer';

export const StaffDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const staffEntity = useAppSelector(state => state.staff.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="staffDetailsHeading">
          <Translate contentKey="movieApp.staff.detail.title">Staff</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{staffEntity.id}</dd>
          <dt>
            <span id="role">
              <Translate contentKey="movieApp.staff.role">Role</Translate>
            </span>
          </dt>
          <dd>{staffEntity.role}</dd>
          <dt>
            <span id="firstName">
              <Translate contentKey="movieApp.staff.firstName">First Name</Translate>
            </span>
          </dt>
          <dd>{staffEntity.firstName}</dd>
          <dt>
            <span id="lastName">
              <Translate contentKey="movieApp.staff.lastName">Last Name</Translate>
            </span>
          </dt>
          <dd>{staffEntity.lastName}</dd>
        </dl>
        <Button tag={Link} to="/staff" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/staff/${staffEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default StaffDetail;
