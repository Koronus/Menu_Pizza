class RestaurantManagementApp {
    constructor() {
        this.baseUrl = 'http://localhost:8080/api/admin';
        this.currentPage = {
            dishes: 1,
            components: 1,
            microelements: 1
        };
        this.pageSize = 10;
        this.currentSort = { field: 'title', direction: 'asc' };
        this.currentFilters = {};

        // Ждем загрузки DOM перед инициализацией
        if (document.readyState === 'loading') {
            document.addEventListener('DOMContentLoaded', () => this.init());
        } else {
            this.init();
        }
    }

    async init() {
        console.log('Initializing app...');

        try {
            this.setupEventListeners();
            await this.loadInitialData();
            this.showSection('dishes');
            console.log('App initialized successfully');
        } catch (error) {
            console.error('Error during initialization:', error);
        }
    }

    setupEventListeners() {
        console.log('Setting up event listeners...');

        // Навигация по разделам
        const navButtons = document.querySelectorAll('.nav-btn');
        if (navButtons.length === 0) {
            console.error('No navigation buttons found!');
            return;
        }

        navButtons.forEach(btn => {
            btn.addEventListener('click', (e) => {
                const section = e.target.dataset.section;
                console.log('Navigation button clicked:', section);
                this.showSection(section);
            });
        });

        // Поиск и фильтрация
        this.addEventListenerIfExists('dish-search', 'input', () => {
            this.debounce(() => this.applyDishFilters(), 300);
        });

        this.addEventListenerIfExists('apply-filters', 'click', () => {
            this.applyDishFilters();
        });

        this.addEventListenerIfExists('reset-filters', 'click', () => {
            this.resetDishFilters();
        });

        // Сортировка
        this.addEventListenerIfExists('dish-sort', 'change', (e) => {
            this.currentSort.field = e.target.value;
            this.loadDishes();
        });

        this.addEventListenerIfExists('sort-asc', 'click', () => {
            this.setSortDirection('asc');
        });

        this.addEventListenerIfExists('sort-desc', 'click', () => {
            this.setSortDirection('desc');
        });

        // Пагинация
        this.addEventListenerIfExists('prev-page', 'click', () => {
            this.previousPage('dishes');
        });

        this.addEventListenerIfExists('next-page', 'click', () => {
            this.nextPage('dishes');
        });

        // Добавление блюда
        this.addEventListenerIfExists('add-dish-btn', 'click', () => {
            console.log('Add dish button clicked');
            this.showDishForm();
        });

        console.log('Event listeners setup completed');
    }

    // Вспомогательный метод для безопасного добавления обработчиков
    addEventListenerIfExists(elementId, event, handler) {
        const element = document.getElementById(elementId);
        if (element) {
            element.addEventListener(event, handler);
            console.log(`EventListener added for: ${elementId}`);
        } else {
            console.warn(`Element not found: ${elementId}`);
        }
    }

    async loadInitialData() {
        try {
            await Promise.all([
                this.loadDishTypes(),
                this.loadDishes()
            ]);
        } catch (error) {
            console.error('Error loading initial data:', error);
            this.showError('Ошибка загрузки初始数据');
        }
    }

    // === УПРАВЛЕНИЕ БЛЮДАМИ ===

    async loadDishes() {
        try {
            console.log('Loading dishes...');
            const response = await fetch(`${this.baseUrl}/dishes`);

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            const dishes = await response.json();
            console.log('Dishes loaded:', dishes);
            this.renderDishes(dishes);
        } catch (error) {
            console.error('Error loading dishes:', error);
            this.showError('Ошибка загрузки блюд: ' + error.message);
        }
    }

    renderDishes(dishes) {
        const tbody = document.getElementById('dishes-tbody');
        if (!tbody) {
            console.error('Dishes table body not found!');
            return;
        }

        if (dishes.length === 0) {
            tbody.innerHTML = '<tr><td colspan="5" style="text-align: center;">Нет данных</td></tr>';
            return;
        }

        tbody.innerHTML = dishes.map(dish => `
            <tr>
                <td>${dish.dishesId || 'N/A'}</td>
                <td>${dish.title || 'Без названия'}</td>
                <td>${dish.price ? dish.price + ' ₽' : '0 ₽'}</td>
                <td>${dish.typeOfDish ? dish.typeOfDish.title : 'Не указан'}</td>
                <td class="actions">
                    <button class="btn-action edit" onclick="app.editDish(${dish.dishesId})">✏️</button>
                    <button class="btn-action delete" onclick="app.deleteDish(${dish.dishesId})">🗑️</button>
                    <button class="btn-action components" onclick="app.manageDishComponents(${dish.dishesId})">🥗</button>
                </td>
            </tr>
        `).join('');

        console.log('Dishes rendered:', dishes.length);
    }

    // ... остальные методы остаются такими же, как в предыдущем ответе

    showSection(sectionName) {
        console.log('Showing section:', sectionName);

        // Скрываем все секции
        document.querySelectorAll('.content-section').forEach(section => {
            section.classList.remove('active');
        });

        // Показываем выбранную секцию
        const targetSection = document.getElementById(`${sectionName}-section`);
        if (targetSection) {
            targetSection.classList.add('active');
        } else {
            console.error(`Section not found: ${sectionName}-section`);
        }

        // Обновляем активную кнопку навигации
        document.querySelectorAll('.nav-btn').forEach(btn => {
            btn.classList.remove('active');
        });

        const activeButton = document.querySelector(`[data-section="${sectionName}"]`);
        if (activeButton) {
            activeButton.classList.add('active');
        }

        // Загружаем данные для секции
        if (sectionName === 'dishes') {
            this.loadDishes();
        }
        // Добавьте загрузку данных для других секций по мере необходимости
    }

    debounce(func, wait) {
        let timeout;
        return function executedFunction(...args) {
            const later = () => {
                clearTimeout(timeout);
                func(...args);
            };
            clearTimeout(timeout);
            timeout = setTimeout(later, wait);
        };
    }
}

// Создаем глобальную переменную для доступа к приложению
window.app = new RestaurantManagementApp();