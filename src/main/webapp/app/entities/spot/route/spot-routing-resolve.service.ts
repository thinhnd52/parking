import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISpot } from '../spot.model';
import { SpotService } from '../service/spot.service';

const spotResolve = (route: ActivatedRouteSnapshot): Observable<null | ISpot> => {
  const id = route.params.id;
  if (id) {
    return inject(SpotService)
      .find(id)
      .pipe(
        mergeMap((spot: HttpResponse<ISpot>) => {
          if (spot.body) {
            return of(spot.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default spotResolve;
