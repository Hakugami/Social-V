import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfilePersonalInfoComponent } from './profile-personal-info.component';

describe('ProfilePersonalInfoComponent', () => {
  let component: ProfilePersonalInfoComponent;
  let fixture: ComponentFixture<ProfilePersonalInfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProfilePersonalInfoComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ProfilePersonalInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
