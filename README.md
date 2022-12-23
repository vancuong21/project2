# project2

CRUD tìm kiếm:

class User(id, name, username, password, avatar, birthdate) - One To Many với UserRole - avatar là upload file

class UserRole(userId, role)

class Student(studentCode, studentId) - One to One với User - studentId là PK cho student, cũng là FK với bảng User
(để @PrimaryKeyJoinColumntrên cột quan hệ entity)
@id
private Integer userId;
@PrimaryKeyJoinColumntrên
@OnetoOne
private User user;

Course(id, name) - Many To Many Student : mỗi học sinh học nhiều môn, mỗi môn nhiều học sinh, do vậy tạo bảng trung gian
ứng với entity sau chưa điểm thi nên phải tạo riêng

Score(id, score, @manytoone Student student, @manytoone Course course)
