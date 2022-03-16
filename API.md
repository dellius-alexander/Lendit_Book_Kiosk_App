<a id="api-docs" href="#"><h1>LendIT Book Kiosk API Reference</h1></a>

<a id="api-requirements-analysis" href="#"><h2>API access paths</h2></a>

<a id="student-actions" href="#"><h3>Student Interactions</h3></a>

---

- Each api below consists of a class id and an api path.
- Access Role: **GUEST, ADMIN, STUDENT, FACULTY, SUPERUSER**

```json
{
  "class id": "api path"
}
```

---

- Student will browse by category

```json
{ 
  "browse-by-category": "/api/v1/book/cat/{category|genre}"
}
```
---

- Students can search by the book title and/or author

```json
{
  "search-by-title": "/api/v1/book/title/{title}",
  "search-by-author": "/api/v1/book/author/{author}",
  "search-by-author-title": "/api/v1/book/author={author}&title={title}"
}
```

---

- Student borrows book

```json
{
  "borrow-by-isbn": "/api/v1/book/borrow/isbn={isbn}&student_id={student_id}",
  "borrow-by-id": "/api/v1/book/borrow/book_id={book_id}&student_id={student_id}"
}
```

---

- Student can return borrowed book

```json
{
  "return-by-id": "/api/b1/book/return/book_id={book_id}&student_id={student_id}",
  "return-by-isbn": "/api/b1/book/return/isbn={isbn}&student_id={student_id}"
}
```

---

- Student will login via laker card or laker id and password

```json
{
  "login-by-id": "/api/v1/student/login/student_id={student_id}&password={password}"
}
```

---

- Student will donate books 

```json
{
  "donate-by-isbn": "/api/v1/book/donate/isbn={isbn}&title={title}"
}
```
