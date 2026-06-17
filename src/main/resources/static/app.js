const form = document.querySelector('#moodForm');
const stressLevel = document.querySelector('#stressLevel');
const stressValue = document.querySelector('#stressValue');
const result = document.querySelector('#result');
const history = document.querySelector('#history');
const count = document.querySelector('#count');
const clearBtn = document.querySelector('#clearBtn');

stressLevel.addEventListener('input', () => {
    stressValue.textContent = stressLevel.value;
});

form.addEventListener('submit', async (event) => {
    event.preventDefault();

    const payload = {
        mood: document.querySelector('#mood').value,
        stressLevel: Number(stressLevel.value),
        availableTime: document.querySelector('#availableTime').value,
        note: document.querySelector('#note').value
    };

    const response = await fetch('/api/recommend', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
    });

    const data = await response.json();
    renderResult(data);
    await loadHistory();
});

clearBtn.addEventListener('click', async () => {
    await fetch('/api/records', { method: 'DELETE' });
    result.innerHTML = 'Submit your mood data first.';
    result.className = 'empty';
    await loadHistory();
});

function renderResult(record) {
    result.className = 'result-box';
    result.innerHTML = `
        <p><span class="risk">Risk: ${escapeHtml(record.riskLevel)}</span></p>
        <h3>${escapeHtml(record.recommendedMeal)}</h3>
        <p>${escapeHtml(record.advice)}</p>
        <p class="meta">Saved at ${escapeHtml(record.createdAt)}</p>
    `;
}

async function loadHistory() {
    const response = await fetch('/api/records');
    const records = await response.json();
    count.textContent = `${records.length} records`;

    if (records.length === 0) {
        history.innerHTML = '<div class="empty">No records yet.</div>';
        return;
    }

    history.innerHTML = records.map(record => `
        <article class="record">
            <p class="meta">#${record.id} • ${escapeHtml(record.createdAt)}</p>
            <p><strong>Mood:</strong> ${escapeHtml(record.mood)} ｜ <strong>Stress:</strong> ${record.stressLevel}/10 ｜ <strong>Time:</strong> ${escapeHtml(record.availableTime)}</p>
            <p><strong>Meal:</strong> ${escapeHtml(record.recommendedMeal)}</p>
            <p><strong>Advice:</strong> ${escapeHtml(record.advice)}</p>
            <p class="meta">Note: ${escapeHtml(record.note || 'No note')}</p>
        </article>
    `).join('');
}

function escapeHtml(value) {
    return String(value)
        .replaceAll('&', '&amp;')
        .replaceAll('<', '&lt;')
        .replaceAll('>', '&gt;')
        .replaceAll('"', '&quot;')
        .replaceAll("'", '&#039;');
}

loadHistory();
