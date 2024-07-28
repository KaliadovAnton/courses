CREATE TABLE IF NOT EXISTS course (
  id SERIAL UNIQUE,
  course_name TEXT NOT NULL,
  PRIMARY KEY(id));

CREATE TABLE IF NOT EXISTS coordinator (
  id SERIAL UNIQUE,
  coordinator_name TEXT NOT NULL,
  PRIMARY KEY(id));

CREATE TABLE IF NOT EXISTS student (
  id SERIAL UNIQUE,
  student_name TEXT NOT NULL,
  coordinator_id BIGINT,
  PRIMARY KEY(id),
  CONSTRAINT fk_coordinator
    FOREIGN KEY(coordinator_id)
      REFERENCES coordinator(id));

CREATE TABLE IF NOT EXISTS course_student (
  course_id BIGINT NOT NULL,
  student_id BIGINT NOT NULL,
  CONSTRAINT fk_course
    FOREIGN KEY(course_id)
      REFERENCES course(id),
  CONSTRAINT fk_student
    FOREIGN KEY(student_id)
      REFERENCES student(id));