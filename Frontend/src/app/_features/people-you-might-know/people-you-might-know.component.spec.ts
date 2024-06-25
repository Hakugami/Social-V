import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PeopleYouMightKnowComponent } from './people-you-might-know.component';

describe('PeopleYouMightKnowComponent', () => {
  let component: PeopleYouMightKnowComponent;
  let fixture: ComponentFixture<PeopleYouMightKnowComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PeopleYouMightKnowComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(PeopleYouMightKnowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
