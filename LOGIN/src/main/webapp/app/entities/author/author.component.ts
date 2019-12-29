import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAuthor } from 'app/shared/model/author.model';
import { AuthorService } from './author.service';
import { AuthorDeleteDialogComponent } from './author-delete-dialog.component';

@Component({
  selector: 'jhi-author',
  templateUrl: './author.component.html'
})
export class AuthorComponent implements OnInit, OnDestroy {
  authors: IAuthor[];
  eventSubscriber: Subscription;

  constructor(protected authorService: AuthorService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll() {
    this.authorService.query().subscribe((res: HttpResponse<IAuthor[]>) => {
      this.authors = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInAuthors();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IAuthor) {
    return item.id;
  }

  registerChangeInAuthors() {
    this.eventSubscriber = this.eventManager.subscribe('authorListModification', () => this.loadAll());
  }

  delete(author: IAuthor) {
    const modalRef = this.modalService.open(AuthorDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.author = author;
  }
}
