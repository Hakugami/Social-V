import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfileEditEmailComponent } from './profile-edit-email.component';

describe('ProfileEditEmailComponent', () => {
  let component: ProfileEditEmailComponent;
  let fixture: ComponentFixture<ProfileEditEmailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProfileEditEmailComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ProfileEditEmailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
