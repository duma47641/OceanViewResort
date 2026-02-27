<%@ page import="java.sql.*" %>
<%
    com.oceanviewresort.Models.StaffMember staffMember = (com.oceanviewresort.Models.StaffMember) session.getAttribute("staffMember");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="Home_Style.css">
    <link rel="stylesheet" href="Register.css">
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@100;300;400;500;700;900&family=Sen:wght@400;700;800&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css">
    <link href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css" rel="stylesheet">
    <title>Edit Selected Reservation</title>
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
    function confirmLogout() {
        return confirm("Are you sure you want to logout?");
    }
</script>


<body>
    <div class="navbar">
        <div class="logo-container">
            <h1 class="logo">OCEAN VIEW RESORT</h1>
        </div>
        <div class="menu-container">
            <ul class="menu-list">
                <li class="menu-list-item"><menu><a class="link-stylings" href="home.jsp">Home</a></menu></li>
                <li class="menu-list-item"><menu><a class="link-stylings" href="roomList?page=viewRoomsAndSearch">Rooms</a></menu></li>
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
                                    <a class="link-styling" href="roomList?page=viewRoomsAndSearch&type=<%= room.getId() %>"><%= room.getName() %></a>
                                </li>
                        <%
                                }
                            }
                        %>
                    </ul>
                </li>



                <li class="menu-list-item"><menu><a class="link-stylings" href="about_us.jsp">About Us</a></menu></li>

                <% if(staffMember == null){ %>
                    <li class="menu-list-item-last"><menu><a class="link-stylings" href="admin_common_login.jsp">Admin Common Login</a></menu></li>
                    <li class="menu-list-item-last"><menu><a class="link-stylings" href="staff_member_common_login.jsp">Staff Member Common Login</a></menu></li>
                <% } else { %>
                    <li class="menu-list-item-last"><menu><a class="link-stylings" href="logoutStaffMember" onclick="return confirmLogout()">Logout <i style="margin-left: 5px;" class="fas fa-sign-out-alt"></i></a></menu></li>
                <% } %>

                <form action="roomList" method="get" class="menu-list-item-search-bar-main">
                    <input type="hidden" name="page" value="viewRoomsAndSearch">
                    <menu><input type="text" name="search" class="menu-list-item-search-bar" placeholder="Search" value="<%= request.getParameter("search") != null ? request.getParameter("search") : "" %>"></menu>
                    <menu><button type="submit" class="search-menu-icon-button"><i class="search-menu-icon fas fa-search"></i></button></menu>
                </form>

            </ul>
        </div>
    </div>

    <div class="sidebar">

        <% if(staffMember == null){ %>

            <div class="menu-item">
                <a href="admin_common_login.jsp"><i class="left-menu-icon material-icons">&#xe8d3;</i></a>
                <span class="submenusidebar">Admin Common Login</span>
            </div>

            <div class="menu-item">
                <a href="staff_member_common_login.jsp"><i class="left-menu-icon fas fa-user"></i></a>
                <span class="submenusidebar">Staff Member Common Login</span>
            </div>

        <% } else { %>

            <div class="menu-item">
                <a href="logoutStaffMember" onclick="return confirmLogout()"><i class="left-menu-icon fas fa-sign-out-alt"></i></a>
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
            <div class="register-form-container">
                <%@ page import="com.oceanviewresort.Models.Reservation" %>
                <%@ page import="com.oceanviewresort.Models.StaffMember" %>
                <%@ page import="com.oceanviewresort.Models.Room" %>
                <%
                    Reservation reservation = (Reservation) request.getAttribute("reservation");
                    Room room = reservation.getRoom();
                    StaffMember staffMember1 = reservation.getStaffMember();
                %>
                <form id="registration" method="post" action="updateReservation" onsubmit="return formValidation()" enctype="multipart/form-data">
                    <h1>UPDATE RESERVATION</h1>
                    <div class="input-box">
                        <input type="number" id="ReservationIDID" name="ReservationIDName" value="<%= reservation.getId() %>" required readonly>
                        <label class="on-top-labels">Reservation ID</label>
                    </div>
                    <div class="input-box">
                        <input type="number" id="StaffMemberIDID" name="StaffMemberIDName" value="<%= staffMember1 != null ? staffMember.getId() : "" %>" required readonly>
                        <label class="on-top-labels">Staff Member ID</label>
                    </div>
                    <div class="input-box">
                        <input type="number" id="RoomIDID" name="RoomIDName" value="<%= room != null ? room.getId() : "" %>" required readonly>
                        <label class="on-top-labels">Room ID</label>
                    </div>
                    <div class="input-box">
                        <input style="padding: 14px 14px 14px 14px;" type="text" id="GuestFullNameID" name="GuestFullNameName" value="<%= reservation != null ? reservation.getGuestFullName() : "" %>" required>
                        <label>Guest Full Name</label>
                    </div>
                    <div class="input-box">
                        <textarea style="padding: 14px 14px 14px 14px;" type="text" id="GuestAddressID" name="GuestAddressName" required><%= reservation != null ? reservation.getGuestAddress() : "" %></textarea>
                        <label>Guest Address</label>
                    </div>
                    <div class="input-box">
                        <input style="padding: 14px 14px 14px 14px;" type="text" id="GuestContactNumberID" name="GuestContactNumberName" value="<%= reservation != null ? reservation.getGuestContactNumber() : "" %>" required>
                        <label>Guest Contact Number</label>
                    </div>
                    <div class="input-box">
                        <input style="padding: 14px 14px 14px 14px;" type="date" id="RoomCheckInDateID" name="RoomCheckInDateName" value="<%= reservation != null ? reservation.getCheckInDate() : "" %>" required readonly>
                        <label class="on-top-labels">Room Check In Date</label>
                    </div>
                    <div class="input-box">
                        <input style="padding: 14px 14px 14px 14px;" type="date" id="RoomCheckOutDateID" name="RoomCheckOutDateName" value="<%= reservation != null ? reservation.getCheckOutDate() : "" %>" required>
                        <label class="on-top-labels">Room Check Out Date</label>
                    </div>
                    <div class="input-box">
                        <input style="padding: 14px 14px 14px 14px;" type="number" id="TotalAmountPayableID" name="TotalAmountPayableName" value="<%= reservation != null ? reservation.getTotalAmount() : "" %>" required readonly>
                        <label class="on-top-labels">Total Amount Payable</label>
                    </div>
                    <button class="register-button" style="font-size:24px" type="submit" name="submit">Update Reservation</button>
                </form>
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
            © <%= java.time.Year.now() %> OCEAN VIEW RESORT. All Rights Reserved.
        </div>
    </div>

    <script>
        function formValidation() {
        const name = document.getElementById("GuestFullNameName").value;
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

    <script>
        // Room price from server
        const pricePerNight = <%= room.getPrice() %>;
    
        const checkIn  = document.getElementById("RoomCheckInDateID");
        const checkOut = document.getElementById("RoomCheckOutDateID");
        const totalField = document.getElementById("TotalAmountPayableID");
    
        // -----------------------------
        // SET MIN CHECK-OUT DATE
        // -----------------------------
        if (checkIn.value) {
            let inDate = new Date(checkIn.value);
            inDate.setDate(inDate.getDate() + 1); // checkout = next day minimum
            checkOut.min = inDate.toISOString().split("T")[0];
        }
    
        // -----------------------------
        // CALCULATE TOTAL WHEN CHECK-OUT CHANGES
        // -----------------------------
        checkOut.addEventListener("change", function () {
        
            if (!checkOut.value) {
                totalField.value = "";
                return;
            }
        
            let inDate  = new Date(checkIn.value);
            let outDate = new Date(checkOut.value);
        
            let diffTime = outDate - inDate;
            let nights = diffTime / (1000 * 60 * 60 * 24);
        
            if (nights <= 0) {
                alert("Check-out date must be after check-in date.");
                checkOut.value = "";
                totalField.value = "";
                return;
            }
        
            let total = nights * pricePerNight;
            totalField.value = total.toFixed(2);
        });
    </script>

</body>
</html>