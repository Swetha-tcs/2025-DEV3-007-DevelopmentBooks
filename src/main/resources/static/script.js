function calculatePrice(event) {
            event.preventDefault();
            const quantities = [
                { title: "Clean Code", count: parseInt(document.querySelector('[name="book1"]').value) },
                { title: "The Clean Coder", count: parseInt(document.querySelector('[name="book2"]').value) },
                { title: "Clean Architecture", count: parseInt(document.querySelector('[name="book3"]').value) },
                { title: "Test Driven Development by Example", count: parseInt(document.querySelector('[name="book4"]').value) },
                { title: "Working Effectively With Legacy Code", count: parseInt(document.querySelector('[name="book5"]').value) }
            ];

            const bookTitles = [];
            quantities.forEach(book => {
                for (let i = 0; i < book.count; i++) {
                    bookTitles.push(book.title);
                }
            });

            fetch('/api/price', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(bookTitles)
            })
            .then(response => response.json())
            .then(price => {
                document.querySelector('.total').textContent = `Total Price: ${price.toFixed(2)} EUR`;
            })
            .catch(err => {
                console.error("Error calculating price:", err);
                document.querySelector('.total').textContent = "Error calculating price.";
            });
        }