import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FriendMiniCardItemComponent } from './friend-mini-card-item.component';

describe('FriendMiniCardItemComponent', () => {
  let component: FriendMiniCardItemComponent;
  let fixture: ComponentFixture<FriendMiniCardItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FriendMiniCardItemComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(FriendMiniCardItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
