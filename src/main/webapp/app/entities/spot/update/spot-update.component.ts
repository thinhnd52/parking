import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ISpot } from '../spot.model';
import { SpotService } from '../service/spot.service';
import { SpotFormGroup, SpotFormService } from './spot-form.service';

@Component({
  standalone: true,
  selector: 'jhi-spot-update',
  templateUrl: './spot-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class SpotUpdateComponent implements OnInit {
  isSaving = false;
  spot: ISpot | null = null;

  protected spotService = inject(SpotService);
  protected spotFormService = inject(SpotFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: SpotFormGroup = this.spotFormService.createSpotFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ spot }) => {
      this.spot = spot;
      if (spot) {
        this.updateForm(spot);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const spot = this.spotFormService.getSpot(this.editForm);
    if (spot.id !== null) {
      this.subscribeToSaveResponse(this.spotService.update(spot));
    } else {
      this.subscribeToSaveResponse(this.spotService.create(spot));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISpot>>): void {
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

  protected updateForm(spot: ISpot): void {
    this.spot = spot;
    this.spotFormService.resetForm(this.editForm, spot);
  }
}
