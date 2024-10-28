import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { ISpot } from '../spot.model';

@Component({
  standalone: true,
  selector: 'jhi-spot-detail',
  templateUrl: './spot-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class SpotDetailComponent {
  spot = input<ISpot | null>(null);

  previousState(): void {
    window.history.back();
  }
}
