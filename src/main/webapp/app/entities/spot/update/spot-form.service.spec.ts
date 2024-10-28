import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../spot.test-samples';

import { SpotFormService } from './spot-form.service';

describe('Spot Form Service', () => {
  let service: SpotFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SpotFormService);
  });

  describe('Service methods', () => {
    describe('createSpotFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSpotFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            spotNo: expect.any(Object),
            level: expect.any(Object),
            spotRow: expect.any(Object),
            spotColumn: expect.any(Object),
            occupied: expect.any(Object),
          }),
        );
      });

      it('passing ISpot should create a new form with FormGroup', () => {
        const formGroup = service.createSpotFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            spotNo: expect.any(Object),
            level: expect.any(Object),
            spotRow: expect.any(Object),
            spotColumn: expect.any(Object),
            occupied: expect.any(Object),
          }),
        );
      });
    });

    describe('getSpot', () => {
      it('should return NewSpot for default Spot initial value', () => {
        const formGroup = service.createSpotFormGroup(sampleWithNewData);

        const spot = service.getSpot(formGroup) as any;

        expect(spot).toMatchObject(sampleWithNewData);
      });

      it('should return NewSpot for empty Spot initial value', () => {
        const formGroup = service.createSpotFormGroup();

        const spot = service.getSpot(formGroup) as any;

        expect(spot).toMatchObject({});
      });

      it('should return ISpot', () => {
        const formGroup = service.createSpotFormGroup(sampleWithRequiredData);

        const spot = service.getSpot(formGroup) as any;

        expect(spot).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISpot should not enable id FormControl', () => {
        const formGroup = service.createSpotFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewSpot should disable id FormControl', () => {
        const formGroup = service.createSpotFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
