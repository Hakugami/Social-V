import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpcomingBirthdayItemComponent } from './upcoming-birthday-item.component';

describe('UpcomingBirthdayItemComponent', () => {
  let component: UpcomingBirthdayItemComponent;
  let fixture: ComponentFixture<UpcomingBirthdayItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UpcomingBirthdayItemComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(UpcomingBirthdayItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
