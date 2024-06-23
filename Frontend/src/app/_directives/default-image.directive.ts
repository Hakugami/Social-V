import { Directive, ElementRef, HostListener, Input } from '@angular/core';

@Directive({
  selector: 'img[defaultImage]',
  standalone: true
})
export class DefaultImageDirective {


  @Input() defaultImage: string = '../../assets/images/user/11.jpg';

  constructor(private el: ElementRef) {}

  @HostListener('error')
  onError() {
    this.el.nativeElement.src = this.defaultImage;
  }
}
