import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PhotoMiniCardItemComponent } from './photo-mini-card-item.component';

describe('PhotoMiniCardItemComponent', () => {
  let component: PhotoMiniCardItemComponent;
  let fixture: ComponentFixture<PhotoMiniCardItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PhotoMiniCardItemComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(PhotoMiniCardItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
