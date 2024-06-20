import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FriendRequestDropdownComponent } from './friend-request-dropdown.component';

describe('FriendRequestDropdownComponent', () => {
  let component: FriendRequestDropdownComponent;
  let fixture: ComponentFixture<FriendRequestDropdownComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FriendRequestDropdownComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(FriendRequestDropdownComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
