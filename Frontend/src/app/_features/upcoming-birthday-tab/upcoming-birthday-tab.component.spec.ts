import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpcomingBirthdayTabComponent } from './upcoming-birthday-tab.component';

describe('UpcomingBirthdayTabComponent', () => {
  let component: UpcomingBirthdayTabComponent;
  let fixture: ComponentFixture<UpcomingBirthdayTabComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UpcomingBirthdayTabComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(UpcomingBirthdayTabComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
