import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { IDriver } from '../driver.model';

@Component({
  standalone: true,
  selector: 'jhi-driver-detail',
  templateUrl: './driver-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class DriverDetailComponent {
  driver = input<IDriver | null>(null);

  previousState(): void {
    window.history.back();
  }
}
