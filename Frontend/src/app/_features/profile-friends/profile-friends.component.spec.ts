import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfileFriendsComponent } from './profile-friends.component';

describe('ProfileFriendsComponent', () => {
  let component: ProfileFriendsComponent;
  let fixture: ComponentFixture<ProfileFriendsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProfileFriendsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ProfileFriendsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
