import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FriendStatusComponent } from './friend-status.component';

describe('FriendStatusComponent', () => {
  let component: FriendStatusComponent;
  let fixture: ComponentFixture<FriendStatusComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FriendStatusComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(FriendStatusComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
