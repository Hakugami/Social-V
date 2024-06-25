import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FriendProfileHeaderComponent } from './friend-profile-header.component';

describe('FriendProfileHeaderComponent', () => {
  let component: FriendProfileHeaderComponent;
  let fixture: ComponentFixture<FriendProfileHeaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FriendProfileHeaderComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(FriendProfileHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
