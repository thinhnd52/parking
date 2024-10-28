import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import SpotResolve from './route/spot-routing-resolve.service';

const spotRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/spot.component').then(m => m.SpotComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/spot-detail.component').then(m => m.SpotDetailComponent),
    resolve: {
      spot: SpotResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/spot-update.component').then(m => m.SpotUpdateComponent),
    resolve: {
      spot: SpotResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/spot-update.component').then(m => m.SpotUpdateComponent),
    resolve: {
      spot: SpotResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default spotRoute;
