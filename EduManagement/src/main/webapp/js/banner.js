document.addEventListener('DOMContentLoaded', () => {
    // Function to handle row highlighting and drag-and-drop for any table
    function initializeTable(tableId) {
        const table = document.querySelector(tableId + ' tbody');
        
        // Highlight clicked row
        const rows = table.querySelectorAll('.clickable-row');
        rows.forEach(row => {
            row.addEventListener('click', () => {
                // Remove existing highlight from other rows
                rows.forEach(r => r.style.backgroundColor = '');

                // Highlight the clicked row
                row.style.backgroundColor = 'lightblue';
            });
        });

        // Function to initialize draggable rows
        function initializeDraggableRows() {
            const rows = Array.from(table.querySelectorAll('.clickable-row')); // Refresh rows array

            rows.forEach((row, index) => {
                row.setAttribute('draggable', 'true'); // Make rows draggable

                // Add event listener for 'dragstart' event
                row.addEventListener('dragstart', (e) => {
                    row.style.opacity = '0.5'; // Make the dragged row semi-transparent
                    e.dataTransfer.setData('text/plain', index); // Store the array index of the dragged row
                });

                // Add event listener for 'dragend' event
                row.addEventListener('dragend', () => {
                    row.style.opacity = '1'; // Reset opacity
                });

                // Add event listener for 'dragover' event
                row.addEventListener('dragover', (e) => {
                    e.preventDefault(); // Allow the row to be dropped
                });

                // Add event listener for 'drop' event
                row.addEventListener('drop', (e) => {
                    e.preventDefault();
                    const draggedRowIndex = e.dataTransfer.getData('text/plain'); // Get the index of the dragged row
                    const draggedRow = rows[draggedRowIndex]; // Access the dragged row from the array

                    // Ensure the dragged row exists and is not the same as the current row
                    if (draggedRow && draggedRow !== row) {
                        const parent = row.parentNode;
                        parent.insertBefore(draggedRow, row); // Insert the dragged row before the current row

                        // Update the playOrder of the rows
                        updatePlayOrder(tableId);

                        // Reinitialize draggable rows to reflect the new order
                        initializeDraggableRows();
                    }
                });
            });
        }

        // Function to update the playOrder of the rows after they are reordered
        function updatePlayOrder(tableId) {
            const updatedRows = Array.from(table.querySelectorAll('.clickable-row'));
            const newOrder = updatedRows.map((row, index) => ({
                BannerID: row.dataset.id, // Update key to "BannerID"
                PlayOrder: index + 1      // Update key to "PlayOrder"
            }));

            // In ra BannerID và PlayOrder để kiểm tra
            console.log("Danh sách cập nhật:");
            newOrder.forEach(order => {
                console.log(`BannerID: ${order.BannerID}, PlayOrder: ${order.PlayOrder}`);
            });

            // Update the playOrder in the table
            updatedRows.forEach((row, index) => {
                const playOrderCell = row.cells[0]; // Assuming playOrder is in the first column
                playOrderCell.textContent = index + 1;
            });

            // Send the new order to the server
            fetch('BannerController?action=updateOrder', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(newOrder)
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Failed to save order');
                    }
                    return response.json();
                })
                .then(data => {
                    console.log('Order updated successfully:', data);
                })
                .catch(error => {
                    console.error('Error updating order:', error);
                });
        }

        // Initialize draggable rows
        initializeDraggableRows();
    }

    // Initialize both table1 and table2
    initializeTable('#table1');
    initializeTable('#table2');
});
