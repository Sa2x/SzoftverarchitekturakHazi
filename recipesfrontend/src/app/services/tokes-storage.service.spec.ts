import { TestBed } from '@angular/core/testing';

import { TokesStorageService } from './tokes-storage.service';

describe('TokesStorageService', () => {
  let service: TokesStorageService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TokesStorageService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
