import { TestBed } from '@angular/core/testing';

import { SharedFriendRequestService } from './shared-friend-request.service';

describe('SharedFriendRequestService', () => {
  let service: SharedFriendRequestService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SharedFriendRequestService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
