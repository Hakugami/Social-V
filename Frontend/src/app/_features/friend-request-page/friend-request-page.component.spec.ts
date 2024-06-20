import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FriendRequestPageComponent } from './friend-request-page.component';

describe('FriendRequestPageComponent', () => {
  let component: FriendRequestPageComponent;
  let fixture: ComponentFixture<FriendRequestPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FriendRequestPageComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(FriendRequestPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
