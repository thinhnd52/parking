import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ISpot } from 'app/entities/spot/spot.model';
import { SpotService } from 'app/entities/spot/service/spot.service';
import { IDriver } from 'app/entities/driver/driver.model';
import { DriverService } from 'app/entities/driver/service/driver.service';
import { CarBrand } from 'app/entities/enumerations/car-brand.model';
import { Color } from 'app/entities/enumerations/color.model';
import { Status } from 'app/entities/enumerations/status.model';
import { TicketService } from '../service/ticket.service';
import { ITicket } from '../ticket.model';
import { TicketFormGroup, TicketFormService } from './ticket-form.service';

@Component({
  standalone: true,
  selector: 'jhi-ticket-update',
  templateUrl: './ticket-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class TicketUpdateComponent implements OnInit {
  isSaving = false;
  ticket: ITicket | null = null;
  carBrandValues = Object.keys(CarBrand);
  colorValues = Object.keys(Color);
  statusValues = Object.keys(Status);

  spotsCollection: ISpot[] = [];
  driversCollection: IDriver[] = [];

  protected ticketService = inject(TicketService);
  protected ticketFormService = inject(TicketFormService);
  protected spotService = inject(SpotService);
  protected driverService = inject(DriverService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: TicketFormGroup = this.ticketFormService.createTicketFormGroup();

  compareSpot = (o1: ISpot | null, o2: ISpot | null): boolean => this.spotService.compareSpot(o1, o2);

  compareDriver = (o1: IDriver | null, o2: IDriver | null): boolean => this.driverService.compareDriver(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ticket }) => {
      this.ticket = ticket;
      if (ticket) {
        this.updateForm(ticket);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ticket = this.ticketFormService.getTicket(this.editForm);
    if (ticket.id !== null) {
      this.subscribeToSaveResponse(this.ticketService.update(ticket));
    } else {
      this.subscribeToSaveResponse(this.ticketService.create(ticket));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITicket>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(ticket: ITicket): void {
    this.ticket = ticket;
    this.ticketFormService.resetForm(this.editForm, ticket);

    this.spotsCollection = this.spotService.addSpotToCollectionIfMissing<ISpot>(this.spotsCollection, ticket.spot);
    this.driversCollection = this.driverService.addDriverToCollectionIfMissing<IDriver>(this.driversCollection, ticket.driver);
  }

  protected loadRelationshipsOptions(): void {
    this.spotService
      .query({ filter: 'ticket-is-null' })
      .pipe(map((res: HttpResponse<ISpot[]>) => res.body ?? []))
      .pipe(map((spots: ISpot[]) => this.spotService.addSpotToCollectionIfMissing<ISpot>(spots, this.ticket?.spot)))
      .subscribe((spots: ISpot[]) => (this.spotsCollection = spots));

    this.driverService
      .query({ filter: 'ticket-is-null' })
      .pipe(map((res: HttpResponse<IDriver[]>) => res.body ?? []))
      .pipe(map((drivers: IDriver[]) => this.driverService.addDriverToCollectionIfMissing<IDriver>(drivers, this.ticket?.driver)))
      .subscribe((drivers: IDriver[]) => (this.driversCollection = drivers));
  }
}
