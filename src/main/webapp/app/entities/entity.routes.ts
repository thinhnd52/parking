import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'authority',
    data: { pageTitle: 'Authorities' },
    loadChildren: () => import('./admin/authority/authority.routes'),
  },
  {
    path: 'ticket',
    data: { pageTitle: 'Tickets' },
    loadChildren: () => import('./ticket/ticket.routes'),
  },
  {
    path: 'spot',
    data: { pageTitle: 'Spots' },
    loadChildren: () => import('./spot/spot.routes'),
  },
  {
    path: 'driver',
    data: { pageTitle: 'Drivers' },
    loadChildren: () => import('./driver/driver.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
