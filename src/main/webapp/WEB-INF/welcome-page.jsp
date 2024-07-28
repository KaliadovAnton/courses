<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, java.text.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
     <head>
        <title>Welcome Page </title>
        <meta charset="utf-8">
        <link rel="stylesheet" href="style.css">
        <style>
           <%@ include file="style.css" %>
        </style>
    </head>
    <body>
        <span class="group">
            <form action="/student" method="POST">
                <span>Add New Student</span><br/>
                <input name="name" placeholder="Enter student name"><br/>
                <button type="submit">Enter</button></br>
            </form>
            <form action="/course" method="POST">
                <span>Add new course</span><br/>
                <input name="name" placeholder="Enter course name"><br/>
                <button type="submit">Enter</button></br>
            </form>
            <form action="/coordinator" method="POST">
                <span>Add new coordinator</span><br/>
                <input name="name" placeholder="Enter coordinator name"><br/>
                <button type="submit">Enter</button></br>
            </form>
        </span>
        <span class="group">
            <form action="/student/update" method="PUT">
                <span>Add New Name For Student</span><br/>
                <input name="id" placeholder="Enter student id"><br/>
                <input name="new_name" placeholder="Enter new student name"><br/>
                <button type="submit">Enter</button></br>
            </form>
            <form action="/course/update" method="PUT">
                <span>Add New Name For Course</span><br/>
                <input name="id" placeholder="Enter course id"><br/>
                <input name="new_name" placeholder="Enter new course name"><br/>
                <button type="submit">Enter</button></br>
            </form>
            <form action="/coordinator/update" method="PUT">
                <span>Add New Name For Coordinator</span><br/>
                 <input name="id" placeholder="Enter coordinator id"><br/>
                <input name="new_name" placeholder="Enter new coordinator name"><br/>
                <button type="submit">Enter</button></br>
            </form>
        </span>
        <span class="group">
            <form action="/student/remove" method="DELETE">
                <span>Remove Student</span><br/>
                <input name="id" placeholder="Enter student id"><br/>
                <button type="submit">Enter</button></br>
            </form>
            <form action="/course/remove" method="DELETE">
                <span>Remove Course</span><br/>
                <input name="id" placeholder="Enter course id"><br/>
                <button type="submit">Enter</button></br>
            </form>
            <form action="/coordinator/remove" method="DELETE">
                <span>Remove Coordinator</span><br/>
                <input name="id" placeholder="Enter coordinator id"><br/>
                <button type="submit">Enter</button></br>
            </form>
        </span>
        <span class="group">
            <form action="/student" method="GET">
                <span>Get Students</span><br/>
                <button type="submit">Enter</button></br>
            </form>
            <form action="/course" method="GET">
                <span>Get Courses</span><br/>
                <button type="submit">Enter</button></br>
            </form>
            <form action="/coordinator" method="GET">
                <span>Get Coordinator</span><br/>
                <button type="submit">Enter</button></br>
            </form>
        </span>
        <span class="group">
            <form action="/student/acquire" method="GET">
                <span>Get Student By Id</span><br/>
                <input name="id" placeholder="Enter student id"><br/>
                <button type="submit">Enter</button></br>
            </form>
            <form action="/coordinator/acquire" method="GET">
                <span>Get Coordinator By Id</span><br/>
                <input name="id" placeholder="Enter coordinator id"><br/>
                <button type="submit">Enter</button></br>
            </form>
            <form action="/course/acquire" method="GET">
                <span>Get Course By Id</span><br/>
                <input name="id" placeholder="Enter course id"><br/>
                <button type="submit">Enter</button></br>
            </form>
        </span>
        <span class="group">
            <form action="/student/update" method="POST">
                <span>Set Coordinator For a Student</span><br/>
                <input name="student_id" placeholder="Enter student id"><br/>
                <input name="coordinator_id" placeholder="Enter coordinator id"><br/>
                <button type="submit">Enter</button></br>
            </form>
            <form action="/course/add" method="POST">
                <span>Set Course For a Student</span><br/>
                <input name="student_id" placeholder="Enter student id"><br/>
                <input name="course_id" placeholder="Enter course id"><br/>
                <button type="submit">Enter</button></br>
            </form>
        </span>
    </body>
</html>