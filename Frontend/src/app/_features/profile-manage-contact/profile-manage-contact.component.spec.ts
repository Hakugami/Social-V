import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfileManageContactComponent } from './profile-manage-contact.component';

describe('ProfileManageContactComponent', () => {
  let component: ProfileManageContactComponent;
  let fixture: ComponentFixture<ProfileManageContactComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProfileManageContactComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ProfileManageContactComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
