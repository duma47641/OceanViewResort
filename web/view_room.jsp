<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.oceanviewresort.Models.Room" %>
<%@ page import="com.oceanviewresort.Models.RoomType" %>

<%@ page import="java.sql.*" %>
<%
    com.oceanviewresort.Models.Admin admin = (com.oceanviewresort.Models.Admin) session.getAttribute("admin");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="Home_Style.css">
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@100;300;400;500;700;900&family=Sen:wght@400;700;800&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css">
    <link href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css" rel="stylesheet">
    <title>View Room</title>
</head>

<script>
    document.addEventListener("DOMContentLoaded", function () {
        const navbar = document.querySelector(".navbar");
        const menu = document.querySelector(".menu-container");
        const hamburger = document.createElement("div");
        hamburger.className = "hamburger";
        hamburger.innerHTML = '<i class="fas fa-bars"></i>';
        navbar.appendChild(hamburger);

        function isMobileView() {
            return window.innerWidth <= 900;
        }

        function syncViewMode() {
            document.body.classList.toggle("mobile-view", isMobileView());
        }

        function resetMenuState() {
            menu.classList.remove("active");
            hamburger.querySelector("i").className = "fas fa-bars";

            document.querySelectorAll(".submenu").forEach(sub => {
                sub.classList.remove("open");
            });

            document.querySelectorAll(".mobile-dropdown i").forEach(icon => {
                icon.className = "fas fa-caret-down";
            });
        }

        hamburger.addEventListener("click", function () {
            if (!isMobileView()) return;

            menu.classList.toggle("active");
            const icon = hamburger.querySelector("i");

            icon.className = menu.classList.contains("active")
            ? "fas fa-times"
            : "fas fa-bars";
        });

        document.querySelectorAll(".mobile-dropdown").forEach(menuItem => {
            menuItem.addEventListener("click", function (e) {
                if (!isMobileView()) return;

                e.preventDefault();

                const parent = menuItem.closest(".menu-list-item");
                const submenu = parent.querySelector(".submenu");
                const icon = menuItem.querySelector("i");

                submenu.classList.toggle("open");
                icon.className = submenu.classList.contains("open")
                ? "fas fa-caret-up"
                : "fas fa-caret-down";
            });
        });

        window.addEventListener("resize", function () {
            syncViewMode();
            if (!isMobileView()) resetMenuState();
        });

        syncViewMode();

    });
</script>

<script>
    function togglePasswordVisibility(fieldId, icon) {
        const input = document.getElementById(fieldId);

        if (input.type === "password") {
            input.type = "text";
            icon.className = "bx bx-hide toggle-password";
        } else {
            input.type = "password";
            icon.className = "bx bx-show toggle-password";
        }
    }
</script>

<body>
    <div class="navbar">
        <div class="logo-container">
            <h1 class="logo">OCEAN VIEW RESORT</h1>
        </div>
        <div class="menu-container">
            <ul class="menu-list">
                <li class="menu-list-item"><menu><a class="link-stylings" href="Home.jsp">Home</a></menu></li>
                <li class="menu-list-item"><menu><a class="link-stylings" href="Rooms.jsp">Rooms</a></menu></li>
                <li class="menu-list-item"><menu class="mobile-dropdown">Room Types <i style="margin-left: 5px;" class="fas fa-caret-down"></i></menu>
                    <ul class="submenu">
                        <%@ page import="java.util.List" %>
                        <%@ page import="com.oceanviewresort.Models.RoomType" %>
                        <%
                            List<RoomType> roomTypes = (List<RoomType>) request.getAttribute("roomTypes");
                            if(roomTypes != null){
                                for(RoomType room : roomTypes){
                        %>
                                <li>
                                    <a class="link-styling" href="#"><%= room.getName() %></a>
                                </li>
                        <%
                                }
                            }
                        %>
                    </ul>
                </li>



                <li class="menu-list-item"><menu><a class="link-stylings" href="about_us.jsp">About Us</a></menu></li>

                <li class="menu-list-item-last"><menu><a class="link-stylings" href="Register.php">Parking</a></menu></li>

                <% if(admin == null){ %>
                    <li class="menu-list-item-last"><menu><a class="link-stylings" href="Parking_Area_View.html">Register</a></menu></li>
                    <li class="menu-list-item-last"><menu><a class="link-stylings" href="Login.php">Login</a></menu></li>
                <% } else { %>
                    <li class="menu-list-item-last"><menu><a class="link-stylings" href="logoutAdmin">Logout <i style="margin-left: 5px;" class="fas fa-sign-out-alt"></i></a></menu></li>
                <% } %>

                <form action="Rooms_Searching.php" method="get" class="menu-list-item-search-bar-main">
                    <menu><input type="text" name="search" class="menu-list-item-search-bar" placeholder="Search"></menu>
                    <menu><button type="submit" class="search-menu-icon-button"><i class="search-menu-icon fas fa-search"></i></button></menu>
                </form>

            </ul>
        </div>
    </div>

    <div class="sidebar">

        <% if(admin == null){ %>

            <div class="menu-item">
                <a href="Register.php"><i class="left-menu-icon fas fa-users"></i></a>
                <span class="submenusidebar">Register</span>
            </div>

            <div class="menu-item">
                <a href="Login.php"><i class="left-menu-icon fas fa-user"></i></a>
                <span class="submenusidebar">Login</span>
            </div>

        <% } else { %>

            <div class="menu-item">
                <a href="logoutAdmin"><i class="left-menu-icon fas fa-sign-out-alt"></i></a>
                <span class="submenusidebar">Logout</span>
            </div>

        <% } %>

    </div>

    <div class="container">
        <div class="content-container">
            <div class="featured-content">
                <img class="featured-title-image" src="img/Ocean_View_Resort_Logo.png" alt="">
                <p class="featured-desc">Welcome to Ocean View Resort, your perfect escape to relaxation and luxury by the sea! Experience tranquility like never before with our beautifully designed rooms, breathtaking ocean views, and world-class hospitality. Whether you are seeking a peaceful getaway or a memorable vacation, Ocean View Resort offers the ideal setting to unwind, refresh, and indulge in comfort.</p>
            </div>

            <h1 class="home-heading">EDIT ROOM LIST</h1>

            <div class="room-table-list-content">
                <div class="filter-bar">
                    <form method="get" action="roomList" class="filter-form">
                        <input type="hidden" name="page" value="<%= request.getParameter("page") %>">
                        <input type="text"
                               name="search"
                               placeholder="Search anything..."
                               value="<%= request.getParameter("search") != null ? request.getParameter("search") : "" %>">
                        <select name="type">
                        <option value="">All Room Types</option>
                        <%
                        String selectedType = request.getParameter("type");
                        List<RoomType> types = (List<RoomType>) request.getAttribute("typeList");
                        if(types != null){
                            for(RoomType t : types){
                                String selected = (selectedType != null && selectedType.equals(String.valueOf(t.getId())))
                                                  ? "selected" : "";
                        %>
                        <option value="<%=t.getId()%>" <%=selected%>><%=t.getName()%></option>
                        <%
                            }
                        }
                        %>
                        </select>
                        <button type="submit">Filter</button>
                    </form>
                </div>


                <div class="table-wrapper">
                    <table class="room-table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Room Type</th>
                                <th>Name</th>
                                <th>Details</th>
                                <th>Price</th>
                                <th>Status</th>
                                <th>Image</th>
                            </tr>
                        </thead>

                        <tbody>
                        <%
                            List<Room> list = (List<Room>) request.getAttribute("roomList");

                            if(list != null && !list.isEmpty()){
                                for(Room r : list){

                                    String base64 = "";
                                    if(r.getImage()!=null){
                                        base64 = java.util.Base64.getEncoder().encodeToString(r.getImage());
                                    }
                        %>

                            <tr>
                                <td><%= r.getId() %></td>
                                <td><%= r.getRoomType().getName() %></td>
                                <td><%= r.getName() %></td>
                                <td><%= r.getDetails() %></td>
                                <td><%= r.getPrice() %></td>
                                <td><%= r.getStatus() %></td>

                                <td>
                                    <% if(!base64.equals("")){ %>
                                        <img src="data:image/jpeg;base64,<%=base64%>" width="80">
                                    <% } else { %>
                                        No Image
                                    <% } %>
                                </td>
                            </tr>

                        <%
                                }
                            } else {
                        %>

                            <tr>
                                <td colspan="8" style="text-align:center; padding:20px;">
                                    No rooms found.
                                </td>
                            </tr>

                        <%
                            }
                        %>
                        </tbody>

                    </table>
                </div>

            </div>


        </div>
    </div>

    <div class="footer">
        <div class="footer-container">
            <div class="footer-brand">
                <h2>OCEAN VIEW RESORT</h2>
                <p>Your ultimate destination for premium rooms, serene surroundings, and unforgettable resort experiences.</p>
            </div>
            <div class="footer-links">
                <h4>Company</h4>
                <ul>
                    <li><a href="#">About Us</a></li>
                    <li><a href="#">Careers</a></li>
                    <li><a href="#">Advertise</a></li>
                </ul>
            </div>
            <div class="footer-links">
                <h4>Legal</h4>
                <ul>
                    <li><a href="#">Privacy Policy</a></li>
                    <li><a href="#">Terms of Use</a></li>
                    <li><a href="#">Home</a></li>
                </ul>
            </div>
            <div class="footer-social">
                <h4>Follow Us</h4>
                <div class="social-icons">
                    <a href="#"><i class="fab fa-facebook-f"></i></a>
                    <a href="#"><i class="fab fa-instagram"></i></a>
                    <a href="#"><i class="fab fa-twitter"></i></a>
                    <a href="#"><i class="fab fa-youtube"></i></a>
                </div>
            </div>

        </div>
        <div class="footer-bottom">
            © <%= java.time.Year.now() %> Cineplex Theatres. All Rights Reserved.
        </div>
    </div>

    <script>
        function formValidation() {
        const name = document.getElementById("FullName").value;
        const password = document.getElementById("password").value;
        const confirm = document.getElementById("confirm_Password").value;
        const nameRegex = /^[A-Za-z ]+$/;
        if (!nameRegex.test(name)) {
            alert("Full name must contain letters only.");
            return false;
        }
        if (password !== confirm) {
            alert("Passwords do not match!");
            return false;
        }
        return true;
    }
    </script>

</body>
</html>