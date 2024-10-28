import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ISpot } from '../spot.model';
import { SpotService } from '../service/spot.service';

@Component({
  standalone: true,
  templateUrl: './spot-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class SpotDeleteDialogComponent {
  spot?: ISpot;

  protected spotService = inject(SpotService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.spotService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
