import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { ISpot } from '../spot.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../spot.test-samples';

import { SpotService } from './spot.service';

const requireRestSample: ISpot = {
  ...sampleWithRequiredData,
};

describe('Spot Service', () => {
  let service: SpotService;
  let httpMock: HttpTestingController;
  let expectedResult: ISpot | ISpot[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(SpotService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a Spot', () => {
      const spot = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(spot).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Spot', () => {
      const spot = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(spot).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Spot', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Spot', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Spot', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addSpotToCollectionIfMissing', () => {
      it('should add a Spot to an empty array', () => {
        const spot: ISpot = sampleWithRequiredData;
        expectedResult = service.addSpotToCollectionIfMissing([], spot);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(spot);
      });

      it('should not add a Spot to an array that contains it', () => {
        const spot: ISpot = sampleWithRequiredData;
        const spotCollection: ISpot[] = [
          {
            ...spot,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addSpotToCollectionIfMissing(spotCollection, spot);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Spot to an array that doesn't contain it", () => {
        const spot: ISpot = sampleWithRequiredData;
        const spotCollection: ISpot[] = [sampleWithPartialData];
        expectedResult = service.addSpotToCollectionIfMissing(spotCollection, spot);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(spot);
      });

      it('should add only unique Spot to an array', () => {
        const spotArray: ISpot[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const spotCollection: ISpot[] = [sampleWithRequiredData];
        expectedResult = service.addSpotToCollectionIfMissing(spotCollection, ...spotArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const spot: ISpot = sampleWithRequiredData;
        const spot2: ISpot = sampleWithPartialData;
        expectedResult = service.addSpotToCollectionIfMissing([], spot, spot2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(spot);
        expect(expectedResult).toContain(spot2);
      });

      it('should accept null and undefined values', () => {
        const spot: ISpot = sampleWithRequiredData;
        expectedResult = service.addSpotToCollectionIfMissing([], null, spot, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(spot);
      });

      it('should return initial array if no Spot is added', () => {
        const spotCollection: ISpot[] = [sampleWithRequiredData];
        expectedResult = service.addSpotToCollectionIfMissing(spotCollection, undefined, null);
        expect(expectedResult).toEqual(spotCollection);
      });
    });

    describe('compareSpot', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareSpot(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareSpot(entity1, entity2);
        const compareResult2 = service.compareSpot(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareSpot(entity1, entity2);
        const compareResult2 = service.compareSpot(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareSpot(entity1, entity2);
        const compareResult2 = service.compareSpot(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
