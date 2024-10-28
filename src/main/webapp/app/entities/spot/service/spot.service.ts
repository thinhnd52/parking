import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISpot, NewSpot } from '../spot.model';

export type PartialUpdateSpot = Partial<ISpot> & Pick<ISpot, 'id'>;

export type EntityResponseType = HttpResponse<ISpot>;
export type EntityArrayResponseType = HttpResponse<ISpot[]>;

@Injectable({ providedIn: 'root' })
export class SpotService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/spots');

  create(spot: NewSpot): Observable<EntityResponseType> {
    return this.http.post<ISpot>(this.resourceUrl, spot, { observe: 'response' });
  }

  update(spot: ISpot): Observable<EntityResponseType> {
    return this.http.put<ISpot>(`${this.resourceUrl}/${this.getSpotIdentifier(spot)}`, spot, { observe: 'response' });
  }

  partialUpdate(spot: PartialUpdateSpot): Observable<EntityResponseType> {
    return this.http.patch<ISpot>(`${this.resourceUrl}/${this.getSpotIdentifier(spot)}`, spot, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISpot>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISpot[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSpotIdentifier(spot: Pick<ISpot, 'id'>): number {
    return spot.id;
  }

  compareSpot(o1: Pick<ISpot, 'id'> | null, o2: Pick<ISpot, 'id'> | null): boolean {
    return o1 && o2 ? this.getSpotIdentifier(o1) === this.getSpotIdentifier(o2) : o1 === o2;
  }

  addSpotToCollectionIfMissing<Type extends Pick<ISpot, 'id'>>(
    spotCollection: Type[],
    ...spotsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const spots: Type[] = spotsToCheck.filter(isPresent);
    if (spots.length > 0) {
      const spotCollectionIdentifiers = spotCollection.map(spotItem => this.getSpotIdentifier(spotItem));
      const spotsToAdd = spots.filter(spotItem => {
        const spotIdentifier = this.getSpotIdentifier(spotItem);
        if (spotCollectionIdentifiers.includes(spotIdentifier)) {
          return false;
        }
        spotCollectionIdentifiers.push(spotIdentifier);
        return true;
      });
      return [...spotsToAdd, ...spotCollection];
    }
    return spotCollection;
  }
}
