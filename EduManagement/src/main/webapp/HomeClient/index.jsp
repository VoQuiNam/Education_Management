<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- 
    - primary meta tag
  -->
<title>EduWeb - The Best Program to Enroll for Exchange</title>
<meta name="title"
	content="EduWeb - The Best Program to Enroll for Exchange">
<meta name="description"
	content="This is an education html template made by codewithsadee">

<!-- 
    - custom css link
  -->
<link rel="stylesheet" href="<c:url value='/css/style.css'/>">
<link rel="stylesheet" href="<c:url value='/css/iconuser.css'/>">

<!-- 
    - google font link
  -->
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=League+Spartan:wght@400;500;600;700;800&family=Poppins:wght@400;500&display=swap"
	rel="stylesheet">

<!-- 
    - preload images
  -->
<link rel="preload" as="image"
	href="<c:url value='/images/hero-bg.svg'/>">
<link rel="preload" as="image"
	href="<c:url value='/images/hero-banner-1.jpg'/>">
<link rel="preload" as="image"
	href="<c:url value='/images/hero-banner-2.jpg'/>">
<link rel="preload" as="image"
	href="<c:url value='/images/hero-shape-1.svg'/>">
<link rel="preload" as="image"
	href="<c:url value='/images/hero-shape-2.svg'/>">

</head>

<body id="top">
	<c:import url="/WEB-INF/fragments/menuclient.jsp" />
	<main>
		<article>

			<!-- 
        - #HERO
      -->

			<section class="section hero has-bg-image" id="home"
				aria-label="home"
				style="background-image: url('<c:url value='/images/hero-bg.svg'/>')">
				<div class="container">

					<div class="hero-content">

						<h1 class="h1 section-title">
							The Best Program to <span class="span">Enroll</span> for Exchange
						</h1>

						<p class="hero-text">Excepteur sint occaecat cupidatat non
							proident sunt in culpa qui officia deserunt mollit.</p>

						<a href="#" class="btn has-before"> <span class="span">Find
								courses</span> <ion-icon name="arrow-forward-outline"
								aria-hidden="true"></ion-icon>
						</a>

					</div>

					<figure class="hero-banner">

						<div class="img-holder one" style="--width: 270; --height: 300;">
							<img src="<c:url value='/images/hero-banner-1.jpg'/>" width="270"
								height="300" alt="hero banner" class="img-cover">
						</div>

						<div class="img-holder two" style="--width: 240; --height: 370;">
							<img src="<c:url value='/images/hero-banner-2.jpg'/>" width="240"
								height="370" alt="hero banner" class="img-cover">
						</div>

						<img src="<c:url value='/images/hero-shape-1.svg'/>" width="380"
							height="190" alt="" class="shape hero-shape-1">

						<img src="<c:url value='/images/hero-shape-2.png'/>" width="622"
							height="551" alt="" class="shape hero-shape-2">

					</figure>

				</div>
			</section>





			<!-- 
        - #CATEGORY
      -->

			<section class="section category" aria-label="category">
				<div class="container">

					<p class="section-subtitle">Categories</p>

					<h2 class="h2 section-title">
						Online <span class="span">Classes</span> For Remote Learning.
					</h2>

					<p class="section-text">Consectetur adipiscing elit sed do
						eiusmod tempor.</p>

					<ul class="grid-list">

						<li>
							<div class="category-card" style="--color: 170, 75%, 41%">

								<div class="card-icon">
									<img src="<c:url value='/images/category-1.svg'/>" width="40"
										height="40" loading="lazy" alt="Online Degree Programs"
										class="img">
								</div>

								<h3 class="h3">
									<a href="#" class="card-title">Online Degree Programs</a>
								</h3>

								<p class="card-text">Lorem ipsum dolor consec tur elit
									adicing sed umod tempor.</p>

								<span class="card-badge">7 Courses</span>

							</div>
						</li>

						<li>
							<div class="category-card" style="--color: 351, 83%, 61%">

								<div class="card-icon">
									<img src="<c:url value='/images/category-2.svg'/>" width="40"
										height="40" loading="lazy" alt="Non-Degree Programs"
										class="img">
								</div>

								<h3 class="h3">
									<a href="#" class="card-title">Non-Degree Programs</a>
								</h3>

								<p class="card-text">Lorem ipsum dolor consec tur elit
									adicing sed umod tempor.</p>

								<span class="card-badge">4 Courses</span>

							</div>
						</li>

						<li>
							<div class="category-card" style="--color: 229, 75%, 58%">

								<div class="card-icon">
									<img src="<c:url value='/images/category-3.svg'/>" width="40"
										height="40" loading="lazy" alt="Off-Campus Programs"
										class="img">
								</div>

								<h3 class="h3">
									<a href="#" class="card-title">Off-Campus Programs</a>
								</h3>

								<p class="card-text">Lorem ipsum dolor consec tur elit
									adicing sed umod tempor.</p>

								<span class="card-badge">8 Courses</span>

							</div>
						</li>

						<li>
							<div class="category-card" style="--color: 42, 94%, 55%">

								<div class="card-icon">
									<img src="<c:url value='/images/category-4.svg'/>" width="40"
										height="40" loading="lazy" alt="Hybrid Distance Programs"
										class="img">
								</div>

								<h3 class="h3">
									<a href="#" class="card-title">Hybrid Distance Programs</a>
								</h3>

								<p class="card-text">Lorem ipsum dolor consec tur elit
									adicing sed umod tempor.</p>

								<span class="card-badge">8 Courses</span>

							</div>
						</li>

					</ul>

				</div>
			</section>

			<!-- 
        - #ABOUT
      -->

			<section class="section about" id="about" aria-label="about">
				<div class="container">

					<figure class="about-banner">

						<div class="img-holder" style="--width: 520; --height: 370;">
							<img src="<c:url value='/images/about-banner.jpg'/>" width="520"
								height="370" loading="lazy" alt="about banner" class="img-cover">
						</div>

						<img src="<c:url value='/images/about-shape-1.svg'/>" width="360"
							height="420" loading="lazy" alt="" class="shape about-shape-1">

						<img src="<c:url value='/images/about-shape-2.svg'/>" width="371"
							height="220" loading="lazy" alt="" class="shape about-shape-2">

						<img src="<c:url value='/images/about-shape-3.png'/>" width="722"
							height="528" loading="lazy" alt="" class="shape about-shape-3">

					</figure>

					<div class="about-content">

						<p class="section-subtitle">About Us</p>

						<h2 class="h2 section-title">
							Over 10 Years in <span class="span">Distant learning</span> for
							Skill Development
						</h2>

						<p class="section-text">Lorem ipsum dolor sit amet consectur
							adipiscing elit sed eiusmod ex tempor incididunt labore dolore
							magna aliquaenim ad minim.</p>

						<ul class="about-list">

							<li class="about-item"><ion-icon
									name="checkmark-done-outline" aria-hidden="true"></ion-icon> <span
								class="span">Expert Trainers</span></li>

							<li class="about-item"><ion-icon
									name="checkmark-done-outline" aria-hidden="true"></ion-icon> <span
								class="span">Online Remote Learning</span></li>

							<li class="about-item"><ion-icon
									name="checkmark-done-outline" aria-hidden="true"></ion-icon> <span
								class="span">Lifetime Access</span></li>

						</ul>

						<img src="<c:url value='/images/about-shape-4.svg'/>" width="100"
							height="100" loading="lazy" alt="" class="shape about-shape-4">

					</div>

				</div>
			</section>

			<!-- 
        - #VIDEO
      -->

			<section class="video has-bg-image" aria-label="video"
				style="background-image: url('<c:url value='/images/video-bg.png'/>')">
				<div class="container">

					<div class="video-card">

						<div class="video-banner img-holder has-after"
							style="--width:; --height:;">
							<img src="<c:url value='/images/video-banner.jpg'/>" width="970"
								height="550" loading="lazy" alt="video banner" class="img-cover">

							<button class="play-btn" aria-label="play video">
								<ion-icon name="play" aria-hidden="true"></ion-icon>
							</button>
						</div>

						<img src="<c:url value='/images/video-shape-1.png'/>" width="1089"
							height="605" loading="lazy" alt="" class="shape video-shape-1">

						<img src="<c:url value='/images/video-shape-2.png'/>" width="158"
							height="174" loading="lazy" alt="" class="shape video-shape-2">

					</div>

				</div>
			</section>





			<!-- 
        - #STATE
      -->

			<section class="section stats" aria-label="stats">
				<div class="container">

					<ul class="grid-list">

						<li>
							<div class="stats-card" style="--color: 170, 75%, 41%">
								<h3 class="card-title">29.3k</h3>

								<p class="card-text">Student Enrolled</p>
							</div>
						</li>

						<li>
							<div class="stats-card" style="--color: 351, 83%, 61%">
								<h3 class="card-title">32.4K</h3>

								<p class="card-text">Class Completed</p>
							</div>
						</li>

						<li>
							<div class="stats-card" style="--color: 260, 100%, 67%">
								<h3 class="card-title">100%</h3>

								<p class="card-text">Satisfaction Rate</p>
							</div>
						</li>

						<li>
							<div class="stats-card" style="--color: 42, 94%, 55%">
								<h3 class="card-title">354+</h3>

								<p class="card-text">Top Instructors</p>
							</div>
						</li>

					</ul>

				</div>
			</section>





			<!-- 
        - #BLOG
      -->

			<section class="section blog has-bg-image" id="blog"
				aria-label="blog"
				style="background-image: url('<c:url value='/images/blog-bg.svg'/>')">
				<div class="container">

					<p class="section-subtitle">Latest Articles</p>

					<h2 class="h2 section-title">Get News With Eduweb</h2>

					<ul class="grid-list">

						<li>
							<div class="blog-card">

								<figure class="card-banner img-holder has-after"
									style="--width: 370; --height: 370;">
									<img src="<c:url value='/images/blog-1.jpg'/>" width="370"
										height="370" loading="lazy"
										alt="Become A Better Blogger: Content Planning"
										class="img-cover">
								</figure>

								<div class="card-content">

									<a href="#" class="card-btn" aria-label="read more"> <ion-icon
											name="arrow-forward-outline" aria-hidden="true"></ion-icon>
									</a> <a href="#" class="card-subtitle">Online</a>

									<h3 class="h3">
										<a href="#" class="card-title">Become A Better Blogger:
											Content Planning</a>
									</h3>

									<ul class="card-meta-list">

										<li class="card-meta-item"><ion-icon
												name="calendar-outline" aria-hidden="true"></ion-icon> <span
											class="span">Oct 10, 2021</span></li>

										<li class="card-meta-item"><ion-icon
												name="chatbubbles-outline" aria-hidden="true"></ion-icon> <span
											class="span">Com 09</span></li>

									</ul>

									<p class="card-text">Lorem Ipsum Dolor Sit Amet Cons Tetur
										Adipisicing Sed.</p>

								</div>

							</div>
						</li>

						<li>
							<div class="blog-card">

								<figure class="card-banner img-holder has-after"
									style="--width: 370; --height: 370;">
									<img src="<c:url value='/images/blog-2.jpg'/>" width="370"
										height="370" loading="lazy"
										alt="Become A Better Blogger: Content Planning"
										class="img-cover">
								</figure>

								<div class="card-content">

									<a href="#" class="card-btn" aria-label="read more"> <ion-icon
											name="arrow-forward-outline" aria-hidden="true"></ion-icon>
									</a> <a href="#" class="card-subtitle">Online</a>

									<h3 class="h3">
										<a href="#" class="card-title">Become A Better Blogger:
											Content Planning</a>
									</h3>

									<ul class="card-meta-list">

										<li class="card-meta-item"><ion-icon
												name="calendar-outline" aria-hidden="true"></ion-icon> <span
											class="span">Oct 10, 2021</span></li>

										<li class="card-meta-item"><ion-icon
												name="chatbubbles-outline" aria-hidden="true"></ion-icon> <span
											class="span">Com 09</span></li>

									</ul>

									<p class="card-text">Lorem Ipsum Dolor Sit Amet Cons Tetur
										Adipisicing Sed.</p>

								</div>

							</div>
						</li>

						<li>
							<div class="blog-card">

								<figure class="card-banner img-holder has-after"
									style="--width: 370; --height: 370;">
									<img src="<c:url value='/images/blog-3.jpg'/>" width="370"
										height="370" loading="lazy"
										alt="Become A Better Blogger: Content Planning"
										class="img-cover">
								</figure>

								<div class="card-content">

									<a href="#" class="card-btn" aria-label="read more"> <ion-icon
											name="arrow-forward-outline" aria-hidden="true"></ion-icon>
									</a> <a href="#" class="card-subtitle">Online</a>

									<h3 class="h3">
										<a href="#" class="card-title">Become A Better Blogger:
											Content Planning</a>
									</h3>

									<ul class="card-meta-list">

										<li class="card-meta-item"><ion-icon
												name="calendar-outline" aria-hidden="true"></ion-icon> <span
											class="span">Oct 10, 2021</span></li>

										<li class="card-meta-item"><ion-icon
												name="chatbubbles-outline" aria-hidden="true"></ion-icon> <span
											class="span">Com 09</span></li>

									</ul>

									<p class="card-text">Lorem Ipsum Dolor Sit Amet Cons Tetur
										Adipisicing Sed.</p>

								</div>

							</div>
						</li>

					</ul>

					<img src="<c:url value='/images/blog-shape.png'/>" width="186"
						height="186" loading="lazy" alt="" class="shape blog-shape">

				</div>
			</section>

		</article>
	</main>



	<c:import url="/WEB-INF/fragments/footerclient.jsp" />


	<!-- 
    - #BACK TO TOP
  -->

	<a href="#top" class="back-top-btn" aria-label="back top top"
		data-back-top-btn> <ion-icon name="chevron-up" aria-hidden="true"></ion-icon>
	</a>





	<!-- 
    - custom js link
  -->
	<script src="<c:url value='/js/script.js'/>" defer></script>

	<!-- 
    - ionicon link
  -->
	<script type="module"
		src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
	<script nomodule
		src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>

</body>

</html>