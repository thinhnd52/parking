import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ITicket, NewTicket } from '../ticket.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITicket for edit and NewTicketFormGroupInput for create.
 */
type TicketFormGroupInput = ITicket | PartialWithRequiredKeyOf<NewTicket>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ITicket | NewTicket> = Omit<T, 'arrivalDate' | 'departureDate'> & {
  arrivalDate?: string | null;
  departureDate?: string | null;
};

type TicketFormRawValue = FormValueOf<ITicket>;

type NewTicketFormRawValue = FormValueOf<NewTicket>;

type TicketFormDefaults = Pick<NewTicket, 'id' | 'arrivalDate' | 'departureDate' | 'damanged'>;

type TicketFormGroupContent = {
  id: FormControl<TicketFormRawValue['id'] | NewTicket['id']>;
  ticketNo: FormControl<TicketFormRawValue['ticketNo']>;
  lastName: FormControl<TicketFormRawValue['lastName']>;
  firstName: FormControl<TicketFormRawValue['firstName']>;
  phone: FormControl<TicketFormRawValue['phone']>;
  room: FormControl<TicketFormRawValue['room']>;
  arrivalDate: FormControl<TicketFormRawValue['arrivalDate']>;
  departureDate: FormControl<TicketFormRawValue['departureDate']>;
  model: FormControl<TicketFormRawValue['model']>;
  color: FormControl<TicketFormRawValue['color']>;
  licensePlate: FormControl<TicketFormRawValue['licensePlate']>;
  status: FormControl<TicketFormRawValue['status']>;
  damanged: FormControl<TicketFormRawValue['damanged']>;
  note: FormControl<TicketFormRawValue['note']>;
  spot: FormControl<TicketFormRawValue['spot']>;
  driver: FormControl<TicketFormRawValue['driver']>;
};

export type TicketFormGroup = FormGroup<TicketFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TicketFormService {
  createTicketFormGroup(ticket: TicketFormGroupInput = { id: null }): TicketFormGroup {
    const ticketRawValue = this.convertTicketToTicketRawValue({
      ...this.getFormDefaults(),
      ...ticket,
    });
    return new FormGroup<TicketFormGroupContent>({
      id: new FormControl(
        { value: ticketRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      ticketNo: new FormControl(ticketRawValue.ticketNo, {
        validators: [Validators.required],
      }),
      lastName: new FormControl(ticketRawValue.lastName, {
        validators: [Validators.required],
      }),
      firstName: new FormControl(ticketRawValue.firstName),
      phone: new FormControl(ticketRawValue.phone, {
        validators: [Validators.required],
      }),
      room: new FormControl(ticketRawValue.room),
      arrivalDate: new FormControl(ticketRawValue.arrivalDate),
      departureDate: new FormControl(ticketRawValue.departureDate),
      model: new FormControl(ticketRawValue.model),
      color: new FormControl(ticketRawValue.color),
      licensePlate: new FormControl(ticketRawValue.licensePlate),
      status: new FormControl(ticketRawValue.status),
      damanged: new FormControl(ticketRawValue.damanged),
      note: new FormControl(ticketRawValue.note),
      spot: new FormControl(ticketRawValue.spot),
      driver: new FormControl(ticketRawValue.driver),
    });
  }

  getTicket(form: TicketFormGroup): ITicket | NewTicket {
    return this.convertTicketRawValueToTicket(form.getRawValue() as TicketFormRawValue | NewTicketFormRawValue);
  }

  resetForm(form: TicketFormGroup, ticket: TicketFormGroupInput): void {
    const ticketRawValue = this.convertTicketToTicketRawValue({ ...this.getFormDefaults(), ...ticket });
    form.reset(
      {
        ...ticketRawValue,
        id: { value: ticketRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): TicketFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      arrivalDate: currentTime,
      departureDate: currentTime,
      damanged: false,
    };
  }

  private convertTicketRawValueToTicket(rawTicket: TicketFormRawValue | NewTicketFormRawValue): ITicket | NewTicket {
    return {
      ...rawTicket,
      arrivalDate: dayjs(rawTicket.arrivalDate, DATE_TIME_FORMAT),
      departureDate: dayjs(rawTicket.departureDate, DATE_TIME_FORMAT),
    };
  }

  private convertTicketToTicketRawValue(
    ticket: ITicket | (Partial<NewTicket> & TicketFormDefaults),
  ): TicketFormRawValue | PartialWithRequiredKeyOf<NewTicketFormRawValue> {
    return {
      ...ticket,
      arrivalDate: ticket.arrivalDate ? ticket.arrivalDate.format(DATE_TIME_FORMAT) : undefined,
      departureDate: ticket.departureDate ? ticket.departureDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
