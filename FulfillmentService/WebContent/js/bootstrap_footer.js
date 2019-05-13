'use strict';

$('.s24-js-toggle').on('click.s24toggle', function (e) {
	e.preventDefault();
	var $target = $($(e.currentTarget).attr('data-target'));
	$target.animate({ height: 'toggle' }, 300);
});