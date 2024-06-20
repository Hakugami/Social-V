import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FriendRequestCardComponent } from './friend-request-card.component';

describe('FriendRequestCardComponent', () => {
  let component: FriendRequestCardComponent;
  let fixture: ComponentFixture<FriendRequestCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FriendRequestCardComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(FriendRequestCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
